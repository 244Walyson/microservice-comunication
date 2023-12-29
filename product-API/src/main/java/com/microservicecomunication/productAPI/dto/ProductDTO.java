package com.microservicecomunication.productAPI.dto;

import com.microservicecomunication.productAPI.entities.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    private SupplierDTO supplierDTO;
    private CategoryDTO categoryDTO;

    public static ProductDTO dto(Product entity){
        var dto = new ProductDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


}
