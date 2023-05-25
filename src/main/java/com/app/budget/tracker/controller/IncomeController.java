package com.app.budget.tracker.controller;


import com.app.budget.tracker.service.IncomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IncomeController {
    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    String showIncome(){
        return "";
    }

    String editIncome(){
        return "";
    }

    String addIncome(){
        return "";
    }

    String saveIncome(){
        return "";
    }

    String deleteIncome(){
        return "";
    }
}
