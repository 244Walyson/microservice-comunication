package com.microservicecomunication.productAPI.repositories;

import com.microservicecomunication.productAPI.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
