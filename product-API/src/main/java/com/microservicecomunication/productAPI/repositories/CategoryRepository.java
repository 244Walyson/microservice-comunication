package com.microservicecomunication.productAPI.repositories;

import com.microservicecomunication.productAPI.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
