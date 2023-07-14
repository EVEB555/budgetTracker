package com.app.budget.tracker.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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


        MvcResult result = mockMvc.perform(
                        post("/incomes")
                                .param("category", "Gift")
                                .param("amount", "10"))
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
    void editIncome() {
    }

    @Test
    void deleteIncome() {
    }
}