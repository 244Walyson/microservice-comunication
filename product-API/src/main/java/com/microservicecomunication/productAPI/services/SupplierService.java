package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.entities.Supplier;
import com.microservicecomunication.productAPI.exception.ValidateException;
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

    public SupplierDTO save(SupplierDTO dto) {
        validateSupplierDto(dto);
        Supplier supp = SupplierDTO.copyDtoToEntity(dto);
        supp = supplierRepository.save(supp);
        return new SupplierDTO().dto(supp);
    }

    private void validateSupplierDto(SupplierDTO dto){
        if(dto.getName().isEmpty()){
            throw new ValidateException("Supplier name should not be null");
        }
    }
}
