package com.app.budget.tracker.controller;


import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.IncomeRepository;
import com.app.budget.tracker.service.IncomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @PostMapping
    public void addIncomeItem(String category, BigDecimal amount){
        service.addIncomeItem(category, amount);
    }

    @GetMapping
    public List<IncomeDTO> getAllIncomeItems(){
        return service.getAllIncomeItems();
    }

    String editIncome(){
        return "";
    }

    String deleteIncome(){
        return "";
    }
}
