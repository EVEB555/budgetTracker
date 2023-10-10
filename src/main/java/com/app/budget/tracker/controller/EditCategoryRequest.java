package com.app.budget.tracker.controller;

public class EditCategoryRequest {
    private String description;

    public EditCategoryRequest() {}

    public EditCategoryRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
