package com.microservicecomunication.productAPI.dto;

import com.microservicecomunication.productAPI.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    private Integer quantityAvailable;
    private SupplierDTO supplier;
    private CategoryDTO category;

    public static ProductDTO dto(Product entity){
        var dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setQuantityAvailable(entity.getQuantityAvailable());
        dto.setCategory(new CategoryDTO().of(entity.getCategory()));
        dto.setSupplier(new SupplierDTO().dto(entity.getSupplier()));
        return dto;
    }

}
