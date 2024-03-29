package com.app.budget.tracker.controller;

import java.math.BigDecimal;

public class EditIncomeRequest {

    private String category;

    private BigDecimal amount;

    public EditIncomeRequest(String category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
