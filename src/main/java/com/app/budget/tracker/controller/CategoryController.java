package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.service.CategoryService;
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
    public void createCategory(@RequestParam(name = "description") String description){
        service.createCategory(description);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return service.getAllCategories();
    }

}
