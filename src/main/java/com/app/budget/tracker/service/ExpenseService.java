package com.app.budget.tracker.service;

import com.app.budget.tracker.entity.Expense;
import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.repository.ExpenseRepository;
import com.app.budget.tracker.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public void addExpenseItem(String category, BigDecimal amount, String comment){
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(amount);
        LocalDate currentDate = LocalDate.now();
        expense.setRecordDate(currentDate);
        expense.setComment(comment); //komentara padaryti nebutina, pvz, jei neduotas, supranta kaip null
        repository.save(expense);
    }
}
