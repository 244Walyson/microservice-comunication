package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.entities.Supplier;
import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.repositories.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);
    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierDTO findById(int id){
        Supplier supplier = supplierRepository.findById(id).get();
        logger.info(supplier.toString());
        return new SupplierDTO().dto(supplier);
    }

    public List<SupplierDTO> findAll(String name){
        List<Supplier> suppliers = supplierRepository.findAllFilter(name);
        return suppliers.stream().map(SupplierDTO::dto).toList();
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
