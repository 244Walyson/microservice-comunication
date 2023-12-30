package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierDTO> findAll(){
        return supplierRepository.findAll().stream().map(SupplierDTO::dto).toList();
    }
}
