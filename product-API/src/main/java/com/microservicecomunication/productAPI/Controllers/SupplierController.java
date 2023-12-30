package com.microservicecomunication.productAPI.Controllers;

import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> findAll(){
        return ResponseEntity.ok(supplierService.findAll());
    }
}
