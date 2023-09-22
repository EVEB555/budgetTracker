package com.app.budget.tracker.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.CategoryRepository;
import com.app.budget.tracker.repository.IncomeRepository;
import com.app.budget.tracker.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ObjectMapper mapper;

    @Test
    void addIncomeItem() throws Exception {
        Income income = new Income();
        Category category = new Category();
        category.setDescription("Salary");
//        category = categoryRepository.save(category);
        // categoryService.createCategory(category.getDescription());
        income.setCategory(category);
        income.setAmount(new BigDecimal(1000));
        income.setRecordDate(LocalDate.now());
        income = repository.save(income); //not used?
        //System.out.println(income);

        CreateIncomeRequest request = new CreateIncomeRequest("Gift", new BigDecimal(100));

        MvcResult result = mockMvc.perform(
                        post("/incomes").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))

                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andReturn();

        List<Income> incomes = repository.findAll();

        assertEquals(2, incomes.size());

        repository.deleteAll();
    }


    @Test
    void getAllIncomeItems() throws Exception {
        //creates fake Object
        var fakeCategory = new Category();
        fakeCategory.setDescription("Gift");

        Income income = new Income();
        income.setAmount(new BigDecimal(10));
        income.setCategory(fakeCategory);
        income.setRecordDate(LocalDate.now());
        repository.save(income);


        //testing functioning of endpoint and storing the result in the result variable

        MvcResult result = this.mockMvc.perform(get("/incomes"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andReturn();


        // This code deserializes the JSON response content from result into a List<IncomeDTO> object using an object mapper,
        // allowing further processing or assertions to be made on the deserialized data.
        List<IncomeDTO> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<IncomeDTO>>() {});

        // assertions made on e.g. on how many results are expected, asserting ID is the same as expected
        assertEquals(1, actual.size());
        assertEquals(income.getId(), actual.get(0).getId());

        repository.deleteAll();
    }



    @Test
    void editIncome() throws Exception {
        // Create a fake income item for editing
        Income fakeIncome = new Income();
        var fakeCategory = new Category();
        fakeCategory.setDescription("Gift");
        fakeIncome.setCategory(fakeCategory);
        fakeIncome.setAmount(new BigDecimal(100));
        fakeIncome.setRecordDate(LocalDate.now());
        repository.save(fakeIncome);

        //Create a request to edit the income item
        EditIncomeRequest editRequest = new EditIncomeRequest("Updated Gift", new BigDecimal(200));

        //Get the ID of the fake income item
        List<Income> incomes = repository.findAll();
        Long incomeId = incomes.get(0).getId(); // Change this to get the ID of the fake income

        //Perform the edit operation
        MvcResult result = mockMvc.perform(
                        put("/incomes/{incomeId}", incomeId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(editRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //Verify that the income item has been edited
        Income editedIncome = repository.findById(incomeId).orElse(null);
        assertNotNull(editedIncome);
        assertEquals("Updated Gift", editedIncome.getCategory().getDescription());
        assertEquals(new BigDecimal(200), editedIncome.getAmount());

        repository.deleteAll();
    }

    @Test
    void deleteIncome() throws Exception {
        // Create a fake income item for deletion
        Income fakeIncome = new Income();
        var fakeCategory = new Category();
        fakeCategory.setDescription("Gift");
        fakeIncome.setCategory(fakeCategory);
        fakeIncome.setAmount(new BigDecimal(100));
        fakeIncome.setRecordDate(LocalDate.now());
        fakeIncome = repository.save(fakeIncome);

        // Get the ID of the fake income item
        Long incomeId = fakeIncome.getId();

        // Perform the delete operation
        mockMvc.perform(
                        delete("/incomes/{incomeId}", incomeId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // Verify that the income item has been deleted
        assertFalse(repository.existsById(incomeId));
    }

}