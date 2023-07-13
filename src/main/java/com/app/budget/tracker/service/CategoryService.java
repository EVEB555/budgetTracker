package com.app.budget.tracker.service;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void createCategory(String description){
        Category category = new Category();
        category.setDescription(description);
        repository.save(category);

    }

    public List<Category> getAllCategories (){
        return repository.findAll();
    }
}
