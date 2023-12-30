package com.microservicecomunication.productAPI.Controllers;

import com.microservicecomunication.productAPI.dto.CategoryDTO;
import com.microservicecomunication.productAPI.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO dto){
        dto = categoryService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable int id){
        return ResponseEntity.ok().body(categoryService.findById(id));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<CategoryDTO>> findByDescription(@PathVariable String description){
        return ResponseEntity.ok().body(categoryService.findByDescription(description));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable int id ,@RequestBody CategoryDTO dto){
        dto = categoryService.update(dto, id);
        return ResponseEntity.ok().body(dto);
    }

}
