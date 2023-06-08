package com.app.budget.tracker.controller;


import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.IncomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class IncomeController {

    private final IncomeRepository repository;

    public IncomeController(IncomeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void addIncomeItem(String category, BigDecimal amount){
        Income income = new Income();
        income.setCategory(category);
        income.setAmount(amount);
        LocalDate currentDate = LocalDate.now();
        income.setRecordDate(currentDate);
        //kaip su category?
        repository.save(income);
    }

    @GetMapping
    public List<IncomeDTO> getAllIncomeItems(){
        List<Income> incomeList = repository.findAll();
        //var incomeList = repository.findAll();
        /*List<IncomeDTO> result = new ArrayList<>();
        for(Income income : all) {
            IncomeDTO item = new IncomeDTO();
            item.setId(income.getId());
            item.setCategory(income.getCategory());
            item.setAmount(income.getAmount());
            result.add(item);
        }*/

        return incomeList.stream().map(income -> {
            IncomeDTO item = new IncomeDTO();
            item.setId(income.getId());
            item.setCategory(income.getCategory());
            item.setAmount(income.getAmount());
            return item;
        }).toList();
    }

    String editIncome(){
        return "";
    }

    String deleteIncome(){
        return "";
    }
}
