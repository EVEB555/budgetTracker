package com.app.budget.tracker.controller;

public class CreateCategoryRequest {
    private String description;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
