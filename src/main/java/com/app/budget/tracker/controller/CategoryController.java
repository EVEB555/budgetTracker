package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void createCategory(@RequestParam(name = "description") String description){
        Category item = new Category();
        item.setDescription(description);
        repository.save(item);

    }

    @GetMapping
    public List<Category> getAllCategories (){
        return repository.findAll();
    }

}
