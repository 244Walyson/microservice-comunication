package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.dto.ProductDTO;
import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.dto.rabbitmq.ProductStockDTO;
import com.microservicecomunication.productAPI.dto.rabbitmq.SalesConfirmationDTO;
import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.entities.Product;
import com.microservicecomunication.productAPI.entities.Supplier;
import com.microservicecomunication.productAPI.enums.SalesStatus;
import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.repositories.CategoryRepository;
import com.microservicecomunication.productAPI.repositories.ProductRepository;
import com.microservicecomunication.productAPI.repositories.SupplierRepository;
import com.microservicecomunication.productAPI.services.rabbitmq.SalesConfirmationSender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public SupplierService supplierService;
    @Autowired
    public SalesConfirmationSender salesConfirmationSender;

    public ProductDTO save(ProductDTO dto){
        validateProductDto(dto);
        Product product = copyDtoToEntity(dto);
        logger.info(product.toString());
        product = productRepository.save(product);
        return new ProductDTO().dto(product);
    }

    public void delete(int id){
        try {
            if (productRepository.existsById(id)) {
                productRepository.delete(productRepository.findById(id).get());
            } else {
                throw new ValidateException("Entity not found");
            }
        }catch (DataIntegrityViolationException e){
            throw new ValidateException("Data integrity violation exception");
        }
    }

    private Product copyDtoToEntity(ProductDTO dto){
        var product = new Product();
        Category cat = new CategoryDTO().copyDtoToEntity(categoryService.findById(dto.getCategory().getId()));
        Supplier supplier = new SupplierDTO().copyDtoToEntity(supplierService.findById(dto.getSupplier().getId()));
        product.setName(dto.getName());
        product.setQuantityAvailable(dto.getQuantityAvailable());
        product.setCategory(cat);
        product.setSupplier(supplier);
        return product;
    }

    private void validateProductDto(ProductDTO dto){
        if(dto.getName().isEmpty()){
            throw new ValidateException("Product coud not be null");
        } else if (dto.getCategory().getId() == null) {
            throw new ValidateException("Category coud not be null");
        } else if (dto.getSupplier().getId() == null) {
            throw new ValidateException("Supplier could not be null");
        }else if (dto.getQuantityAvailable() == null || dto.getQuantityAvailable() <= ZERO){
            throw new ValidateException("Quantity available must be zero or greater than zero and not null");
        }
    }

    public ProductDTO update(ProductDTO dto, int id) {
        validateProductDto(dto);
        Product prod = productRepository.findById(id).get();
        prod.setName(dto.getName());
        prod.setQuantityAvailable(dto.getQuantityAvailable());
        Category cat = new CategoryDTO().copyDtoToEntity(categoryService.findById(dto.getCategory().getId()));
        Supplier supplier = new SupplierDTO().copyDtoToEntity(supplierService.findById(dto.getSupplier().getId()));
        prod.setSupplier(supplier);
        prod.setCategory(cat);
        prod = productRepository.save(prod);
        return new ProductDTO().dto(prod);
    }

    public List<ProductDTO> findAll() {
        List<Product> prod = productRepository.findAll();
        return prod.stream().map(ProductDTO::dto).toList();
    }

    public void updateProductStock(ProductStockDTO dto){
        try {
            validateProductStockDTO(dto);
            updateStock(dto);
            salesConfirmationSender.sendSalesConfirmationMessage(new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED));
        }catch (Exception e){
            logger.info("Error while trying to update stock for message with error: {}", e.getMessage());
            salesConfirmationSender.sendSalesConfirmationMessage(new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.REJECTED));
        }
    }

    public void updateStock(ProductStockDTO dto){
        dto
                .getProducts()
                .forEach(salesProduct -> {
                    Optional<Product> product = productRepository.findById(salesProduct.getProductId());
                    if(product.isEmpty()){
                        throw new ValidateException("The product with id " + salesProduct.getProductId() + " does not exists");
                    }
                    if (salesProduct.getQuantity() > product.get().getQuantityAvailable()) {
                        throw new ValidateException("The product "+ salesProduct.getProductId() +" is out of stock");
                    }
                    product.get().updateStock(salesProduct.getQuantity());
                    productRepository.save(product.get());
                });
    }
    public void validateProductStockDTO(ProductStockDTO dto){
        if(dto == null || dto.getSalesId().isEmpty()){
            throw new ValidateException("The product data or sales id cannot be null");
        }
        if(dto.getProducts().isEmpty()){
            throw new ValidateException("The sales` product must be informed");
        }
        dto.getProducts()
                .forEach(salesProduct -> {
                    if(salesProduct.getQuantity() == null || salesProduct.getQuantity() == ZERO || salesProduct.getProductId() == null){
                        throw new ValidateException("The productId and the quantity must be informed");
                    }
        });
    }

}
