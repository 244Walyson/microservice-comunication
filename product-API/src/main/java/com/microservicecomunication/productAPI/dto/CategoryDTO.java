package com.microservicecomunication.productAPI.dto;

import com.microservicecomunication.productAPI.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryDTO {

    private Integer id;
    private String description;

    public static CategoryDTO dto(Category entity){
        var dto = new CategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
