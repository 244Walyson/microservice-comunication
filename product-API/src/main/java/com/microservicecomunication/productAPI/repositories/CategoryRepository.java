package com.microservicecomunication.productAPI.repositories;

import com.microservicecomunication.productAPI.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT category.id, category.description FROM category WHERE category.description LIKE CONCAT('%', :description, '%');
        """)
    List<Category> findAllByDescription(@Param("description") String description);}
