package com.microservicecomunication.productAPI.repositories;

import com.microservicecomunication.productAPI.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
