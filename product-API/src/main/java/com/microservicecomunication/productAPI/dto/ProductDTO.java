package com.microservicecomunication.productAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservicecomunication.productAPI.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierDTO supplier;
    private CategoryDTO category;

    public static ProductDTO dto(Product entity){
        var dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setQuantityAvailable(entity.getQuantityAvailable());
        dto.setCategory(new CategoryDTO().of(entity.getCategory()));
        dto.setSupplier(new SupplierDTO().dto(entity.getSupplier()));
        return dto;
    }

}
