package com.app.budget.tracker.controller;

import java.math.BigDecimal;

//CreateIncomeRequest class is a data transfer object (DTO) used to encapsulate the data required to create a new income item in your application.
// Its purpose is to provide a structured and consistent way to pass information from the client
// (usually a front-end application or an external API) to a server when a new income item is being added.
//In  IncomeController, when a client wants to create a new income item, it would send an HTTP POST request with a JSON representation of this CreateIncomeRequest object in the request body.
// Using DTOs like CreateIncomeRequest helps in keeping API well-structured, maintainable, and allows for clear separation of concerns between the client and server components of your application.
// It also helps in ensuring that the data passed between them is well-defined and follows any necessary validation rules.
public class CreateIncomeRequest {

    private String category;

    private BigDecimal amount;

    public CreateIncomeRequest(String category, BigDecimal amount) {
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
