package com.microservicecomunication.productAPI.repositories;

import com.microservicecomunication.productAPI.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT sup.id, sup.name FROM supplier sup WHERE sup.name LIKE CONCAT('%', :name, '%');
            """)
    List<Supplier> findAllFilter(@Param("name") String name);
}
