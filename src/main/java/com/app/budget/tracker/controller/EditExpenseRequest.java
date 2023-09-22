package com.app.budget.tracker.controller;

import java.math.BigDecimal;

public class EditExpenseRequest {
    private String category;
    private BigDecimal amount;
    private String comment;

    public EditExpenseRequest() {}

    public EditExpenseRequest(String category, BigDecimal amount, String comment) {
        this.category = category;
        this.amount = amount;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

