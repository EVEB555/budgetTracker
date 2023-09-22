package com.app.budget.tracker.service;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.entity.Expense;
import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.CategoryDTO;
import com.app.budget.tracker.model.ExpenseDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.repository.ExpenseRepository;
import com.app.budget.tracker.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository repository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository repository, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    public void addExpenseItem(String category, BigDecimal amount, String comment) {
        Expense expense = new Expense();
        Category existingCategory = categoryRepository.findByDescription(category);

        if (existingCategory != null && existingCategory.getDescription().equals(category)) {
            expense.setCategory(existingCategory);
        } else {
            var createdCategory = categoryService.createCategory(category);
            expense.setCategory(createdCategory);
        }

        expense.setAmount(amount);
        LocalDate currentDate = LocalDate.now();
        expense.setRecordDate(currentDate);
        expense.setComment(comment);
        repository.save(expense);
    }

    public List<ExpenseDTO> getAllExpenseItems() {
        List<Expense> expenseList = repository.findAll();

        return expenseList.stream().map(expense -> {
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(expense.getCategory().getId());
            categoryDTO.setDescription(expense.getCategory().getDescription());

            ExpenseDTO item = new ExpenseDTO();
            item.setId(expense.getId());
            item.setCategory(categoryDTO);
            item.setAmount(expense.getAmount());
            item.setRecordDate(expense.getRecordDate());
            item.setComment(expense.getComment());
            return item;
        }).toList();
    }


    public void editExpense(Long expenseId, String category, BigDecimal amount, String comment) {
        Expense expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense item not found with id: " + expenseId));

        Category existingCategory = categoryRepository.findByDescription(category);

        if (existingCategory != null && existingCategory.getDescription().equals(category)) {
            expense.setCategory(existingCategory);
        } else {
            var createdCategory = categoryService.createCategory(category);
            expense.setCategory(createdCategory);
        }

        expense.setAmount(amount);
        expense.setComment(comment);
        repository.save(expense);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense item not found with id: " + expenseId));

        repository.delete(expense);
    }
}
