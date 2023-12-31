package com.microservicecomunication.productAPI.clients;

import com.microservicecomunication.productAPI.dto.SalesProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "salesCLient",
        contextId = "salesClient",
        url = "${app-config.service.sales}"
)
public interface SalesClient {

    @GetMapping("/products/{productId}")
    Optional<SalesProductDTO> findSalesByProductId(@PathVariable Integer productId);
}
