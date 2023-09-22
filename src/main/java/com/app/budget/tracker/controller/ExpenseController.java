package com.app.budget.tracker.controller;

import com.app.budget.tracker.model.ExpenseDTO;
import com.app.budget.tracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public void addExpenseItem(@RequestBody CreateExpenseRequest request) {
        service.addExpenseItem(request.getCategory(), request.getAmount(), request.getComment());
    }

    @GetMapping
    public List<ExpenseDTO> getAllExpenseItems() {
        return service.getAllExpenseItems();
    }

    @PutMapping("/{expenseId}")
    public void editExpense(@PathVariable Long expenseId, @RequestBody EditExpenseRequest request) {
        service.editExpense(expenseId, request.getCategory(), request.getAmount(), request.getComment());
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable Long expenseId) {
        service.deleteExpense(expenseId);
    }
}
