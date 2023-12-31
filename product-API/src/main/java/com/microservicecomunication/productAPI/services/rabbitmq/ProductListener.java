package com.microservicecomunication.productAPI.services.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicecomunication.productAPI.dto.rabbitmq.ProductStockDTO;
import com.microservicecomunication.productAPI.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO message) throws JsonProcessingException {
        log.info("message received and transactionid" + new ObjectMapper().writeValueAsString(message));
        productService.updateProductStock(message);
    }
}
