package com.microservicecomunication.productAPI.services.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicecomunication.productAPI.dto.rabbitmq.SalesConfirmationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;
    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationMq;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message){
        try {
            log.info("sending Message: " + new ObjectMapper().writeValueAsString(message));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationMq, message);
            log.info("Success on send message");
        }catch (Exception e){
            log.info("Error while sending message: " + e.getMessage());
        }
    }
}
