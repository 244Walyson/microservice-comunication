package com.microservicecomunication.productAPI.services;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.entities.Category;
import com.microservicecomunication.productAPI.exception.ValidateException;
import com.microservicecomunication.productAPI.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO findById(int id){
        return new CategoryDTO().of(categoryRepository.findById(id).get());
    }

    public List<CategoryDTO> findAll(){
        return categoryRepository.findAll().stream().map(CategoryDTO::of).toList();
    }

    public CategoryDTO save(CategoryDTO dto){
        validateCategoryDto(dto);
        var category = CategoryDTO.copyDtoToEntity(dto);
        category = categoryRepository.save(category);
        return new CategoryDTO().of(category);
    }

    public List<CategoryDTO> findByDescription(String description) {
        List<Category> categories = categoryRepository.findAllByDescription(description);
        return categories.stream().map(CategoryDTO::of).toList();
    }

    public void delete(int id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                categoryRepository.delete(category.get());
            } else {
                throw new ValidateException("Entity not found");
            }
        }catch (DataIntegrityViolationException e){
            throw new ValidateException("Data integrity violation exception");
        }
    }

    private void validateCategoryDto(CategoryDTO dto){
        if(dto.getDescription().isEmpty()){
            throw new ValidateException("The category description was not informed");
        }
    }

}
