package com.microservicecomunication.productAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservicecomunication.productAPI.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSalesDTO {

    private Integer id;
    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierDTO supplier;
    private CategoryDTO category;
    private List<String> sales;

    public static ProductSalesDTO of(Product entity, List<String> sales){
        var dto = new ProductSalesDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setQuantityAvailable(entity.getQuantityAvailable());
        dto.setCategory(new CategoryDTO().of(entity.getCategory()));
        dto.setSupplier(new SupplierDTO().dto(entity.getSupplier()));
        dto.setSales(sales);
        return dto;
    }

}
