package com.app.budget.tracker.controller;


import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.IncomeRepository;
import com.app.budget.tracker.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    public void addIncomeItem(@RequestBody CreateIncomeRequest request){
        service.addIncomeItem(request.getCategory(), request.getAmount());
    }

    @GetMapping
    public List<IncomeDTO> getAllIncomeItems(){
        return service.getAllIncomeItems();
    }


    @PutMapping("/{incomeId}")
    public void editIncome(@PathVariable Long incomeId, @RequestBody EditIncomeRequest request) {
        service.editIncome(incomeId, request.getCategory(), request.getAmount());
    }

    @DeleteMapping("/{incomeId}")
    public void deleteIncome(@PathVariable Long incomeId) {
        service.deleteIncome(incomeId);
    }


}


