package com.microservicecomunication.productAPI.dto.rabbitmq;

import com.microservicecomunication.productAPI.enums.SalesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesConfirmationDTO {

    private String salesId;
    private SalesStatus status;
}
