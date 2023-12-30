package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.dto.ProductDTO;
import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.entities.Product;
import com.microservicecomunication.productAPI.entities.Supplier;
import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.repositories.CategoryRepository;
import com.microservicecomunication.productAPI.repositories.ProductRepository;
import com.microservicecomunication.productAPI.repositories.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProductDTO save(ProductDTO dto){
        validateProductDto(dto);
        Product product = copyDtoToEntity(dto);
        logger.info(product.toString());
        product = productRepository.save(product);
        return new ProductDTO().dto(product);
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

}
