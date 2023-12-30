package com.microservicecomunication.productAPI.dto;

import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.entities.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierDTO {

    private Integer id;
    private String name;

    public static SupplierDTO dto(Supplier entity){
        var dto = new SupplierDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static Supplier copyDtoToEntity(SupplierDTO dto){
        var supplier = new Supplier();
        supplier.setName(dto.getName());
        return supplier;
    }

}
