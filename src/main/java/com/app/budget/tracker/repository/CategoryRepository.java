package com.app.budget.tracker.repository;

import com.app.budget.tracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    //category here = description as category type is identified by description value in Category class
    Category findByDescription(String category);
}
