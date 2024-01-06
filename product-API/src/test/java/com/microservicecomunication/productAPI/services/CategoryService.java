package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.entities.Product;
import com.microservicecomunication.productAPI.repositories.CategoryRepository;
import com.microservicecomunication.productAPI.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class CategoryService {

    @InjectMocks
    private CategoryService service;
    @Mock
    private CategoryRepository repository;

    private Category category;
    private CategoryDTO categoryDTO;
    private Integer existingId;
    private Integer nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1;
        nonExistingId = 2;
        category = new Category(1, "category 1");
        categoryDTO = new CategoryDTO().of(category);

        Mockito.when(repository.save(any())).thenReturn(category);
        Mockito.when(repository.findAll()).thenReturn(List.of(category));
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
    }


}
