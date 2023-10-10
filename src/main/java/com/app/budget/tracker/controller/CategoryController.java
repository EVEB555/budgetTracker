package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.model.CategoryDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public void addCategory(@RequestBody CreateCategoryRequest request) {
        service.createCategory(request.getDescription());
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return service.getAllCategories();
    }

    @PutMapping("/{categoryId}")
    public void editCategory(@PathVariable Long categoryId, @RequestBody EditCategoryRequest request) {
        service.editCategory(categoryId, request.getDescription());
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
    }
}
