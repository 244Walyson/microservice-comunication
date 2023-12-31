package com.microservicecomunication.productAPI.Controllers;

import com.microservicecomunication.productAPI.dto.ProductCheckStockDTO;
import com.microservicecomunication.productAPI.dto.ProductDTO;
import com.microservicecomunication.productAPI.dto.ProductSalesDTO;
import com.microservicecomunication.productAPI.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO dto){
        dto = productService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable int id, @RequestBody ProductDTO dto){
        dto = productService.update(dto, id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/sales")
    public ResponseEntity<ProductSalesDTO> findProductSales(@PathVariable Integer id){
        return ResponseEntity.ok().body(productService.findProductSales(id));
    }

    @PostMapping("/check-stock")
    public ResponseEntity productCheckStock(@RequestBody ProductCheckStockDTO dto){
        if (productService.productCheckStock(dto).getStatusCode().value() == HttpStatus.OK.value()){
            return ResponseEntity.ok().body("The stock is ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
