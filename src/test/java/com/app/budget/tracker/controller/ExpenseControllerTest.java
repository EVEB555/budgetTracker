package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.entity.Expense;
import com.app.budget.tracker.model.ExpenseDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.repository.ExpenseRepository;
import com.app.budget.tracker.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void addExpenseItem() throws Exception {
        Expense expense = new Expense();
        Category category = new Category();
        category.setDescription("Groceries");
        expense.setCategory(category);
        expense.setAmount(new BigDecimal(50));
        expense.setRecordDate(LocalDate.now());
        expense = repository.save(expense);

        CreateExpenseRequest request = new CreateExpenseRequest("Shopping", new BigDecimal(30), "Clothes");

        MvcResult result = mockMvc.perform(
                        post("/expenses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Expense> expenses = repository.findAll();
        assertEquals(2, expenses.size());

        repository.deleteAll();
    }

    @Test
    void getAllExpenseItems() throws Exception {
        var fakeCategory = new Category();
        fakeCategory.setDescription("Shopping");

        Expense expense = new Expense();
        expense.setAmount(new BigDecimal(40));
        expense.setCategory(fakeCategory);
        expense.setRecordDate(LocalDate.now());
        repository.save(expense);

        MvcResult result = mockMvc.perform(get("/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<ExpenseDTO> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ExpenseDTO>>() {});

        assertEquals(1, actual.size());
        assertEquals(expense.getId(), actual.get(0).getId());

        repository.deleteAll();
    }

    @Test
    void editExpense() throws Exception {
        Expense fakeExpense = new Expense();
        var fakeCategory = new Category();
        fakeCategory.setDescription("Shopping");
        fakeExpense.setCategory(fakeCategory);
        fakeExpense.setAmount(new BigDecimal(100));
        fakeExpense.setRecordDate(LocalDate.now());
        repository.save(fakeExpense);

        EditExpenseRequest editRequest = new EditExpenseRequest("Updated Shopping", new BigDecimal(200), "Updated Comment");

        List<Expense> expenses = repository.findAll();
        Long expenseId = expenses.get(0).getId();

        MvcResult result = mockMvc.perform(
                        put("/expenses/{expenseId}", expenseId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(editRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Expense editedExpense = repository.findById(expenseId).orElse(null);
        assertNotNull(editedExpense);
        assertEquals("Updated Shopping", editedExpense.getCategory().getDescription());
        assertEquals(new BigDecimal(200), editedExpense.getAmount());
        assertEquals("Updated Comment", editedExpense.getComment());

        repository.deleteAll();
    }

    @Test
    void deleteExpense() throws Exception {
        Expense fakeExpense = new Expense();
        var fakeCategory = new Category();
        fakeCategory.setDescription("Shopping");
        fakeExpense.setCategory(fakeCategory);
        fakeExpense.setAmount(new BigDecimal(100));
        fakeExpense.setRecordDate(LocalDate.now());
        fakeExpense = repository.save(fakeExpense);

        Long expenseId = fakeExpense.getId();

        mockMvc.perform(
                        delete("/expenses/{expenseId}", expenseId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(repository.existsById(expenseId));
    }
}
