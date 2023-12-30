package com.microservicecomunication.productAPI.Controllers;

import com.microservicecomunication.productAPI.dto.SupplierDTO;
import com.microservicecomunication.productAPI.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name){
        return ResponseEntity.ok(supplierService.findAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> findById(@PathVariable int id){
        return ResponseEntity.ok().body(supplierService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> save(@RequestBody SupplierDTO dto){
        dto = supplierService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
