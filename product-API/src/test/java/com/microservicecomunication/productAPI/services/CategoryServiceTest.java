package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.dto.ProductDTO;
import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;
    @Mock
    private CategoryRepository repository;

    private Category category;
    private CategoryDTO categoryDTO;
    private Integer existingId;
    private Integer nonExistingId;
    private Integer dependecyId;
    private List<Category> categories;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1;
        nonExistingId = 2;
        dependecyId = 100;
        category = new Category(1, "category 1");
        categoryDTO = new CategoryDTO().of(category);
        categories = Arrays.asList(category);

        Mockito.when(repository.save(any())).thenReturn(category);
        Mockito.when(repository.findAll()).thenReturn(categories);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(dependecyId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.doNothing().when(repository).delete(any());
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependecyId);
    }

    @Test
    public void findAllShouldReturnListOfCategoryDto() throws Exception {
        List<CategoryDTO> result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categories.size(), result.size());
        Assertions.assertEquals(categories.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(categories.get(0).getDescription(), result.get(0).getDescription());
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenExistingId() throws Exception {
        CategoryDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.getId(), result.getId());
        Assertions.assertEquals(category.getDescription(), result.getDescription());
    }

    @Test
    public void findByIdShouldThrowsEntityNotFoundExceptionWhenNonExistingId() throws Exception{
        Assertions.assertThrows(EntityNotFoundException.class,() -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnCategoryDtoWhenValidData() throws Exception {
        CategoryService serviceSpy = Mockito.spy(service);
        Mockito.doNothing().when(serviceSpy).validateCategoryDto(categoryDTO);

        CategoryDTO result = service.save(categoryDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(categoryDTO.getDescription(), result.getDescription());
    }

    @Test
    public void insertShouldThrowsValidateExceptionWhenInvalidData() throws Exception {
        categoryDTO.setDescription("");
        CategoryService serviceSpy = Mockito.spy(service);
        Mockito.doThrow(ValidateException.class).when(serviceSpy).validateCategoryDto(categoryDTO);

        Assertions.assertThrows(ValidateException.class, () -> {
            service.save(categoryDTO);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenExistingId() throws Exception {
        Assertions.assertDoesNotThrow(()->{
            service.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowsValidationExceptionWhenDependencyId() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
           service.delete(dependecyId);
        });
    }

    @Test
    public void deleteShouldThrowsEntityNotFoundExceptionWhenNonExistingId() throws Exception {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
           service.delete(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnCategoryDtoWhenValidData() throws Exception {
        CategoryService serviceSpy = Mockito.spy(service);
        Mockito.doNothing().when(serviceSpy).validateCategoryDto(categoryDTO);

        CategoryDTO result = service.update(categoryDTO, existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
    }

    @Test
    public void updateShouldThrowValidateExceptionWhenINvalidData() throws Exception {
        CategoryService serviceSpy = Mockito.spy(service);
        categoryDTO.setDescription("");
        Mockito.doThrow(ValidateException.class).when(serviceSpy).validateCategoryDto(categoryDTO);

        Assertions.assertThrows(ValidateException.class, () -> {
           service.update(categoryDTO, existingId);
        });
    }

    @Test
    public void updateShouldReturnThrowEntityNotFoundExceptionWhenIdNonExisting() throws Exception{
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.update(categoryDTO, nonExistingId);
        });

    }
}
