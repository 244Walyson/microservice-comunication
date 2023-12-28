package com.microservicecomunication.productAPI.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class statusController {

    @GetMapping("/status")
    public ResponseEntity<HashMap<String, Object>> getApiStatus(){
        HashMap<String, Object> respponse = new HashMap<String, Object>();

        respponse.put("Service", "Product-APi");
        respponse.put("Status", "up");
        respponse.put("httpStatus", HttpStatus.OK.value());

        return ResponseEntity.ok(respponse);
    }

}
