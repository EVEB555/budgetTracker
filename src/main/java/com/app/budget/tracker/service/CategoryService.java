package com.app.budget.tracker.service;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.model.CategoryDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }


    public Category createCategory(String description){
        Category category = new Category();
        category.setDescription(description);
        return repository.save(category);
    }

   /* public List<Category> getAllCategories (){
        return repository.findAll();
    }*/

    //NEW
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = repository.findAll();

        return categoryList.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setDescription(category.getDescription());
            return categoryDTO;
        }).collect(Collectors.toList());
    }

    public void editCategory(Long categoryId, String description) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        category.setDescription(description);
        repository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        repository.delete(category);
    }

}


