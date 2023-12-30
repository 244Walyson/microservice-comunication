package com.microservicecomunication.productAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;
    private Integer quantityAvailable;
    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;
    @Column(columnDefinition = "TIMESTAMP",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
