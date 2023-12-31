package com.microservicecomunication.productAPI.dto;

import com.microservicecomunication.productAPI.dto.rabbitmq.ProductQuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCheckStockDTO {

    private List<ProductQuantityDTO> products;
}
