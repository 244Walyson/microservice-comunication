package com.microservicecomunication.productAPI.clients;

import com.microservicecomunication.productAPI.dto.SalesProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "salesClient",
        contextId = "salesClient",
        url = "${app-config.services.sales}"
)
public interface SalesClient {

    @GetMapping("/api/orders/products/{id}")
    Optional<SalesProductDTO> findSalesByProductId(@PathVariable Integer id);
}
