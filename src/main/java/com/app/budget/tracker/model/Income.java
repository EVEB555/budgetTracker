package com.app.budget.tracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Income {

    private UUID id; //ar makes sense?
    private String category;
    private BigDecimal amount;
    private LocalDate recordDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category; //kaip cia dabar su ta kategorija
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}
