package com.app.budget.tracker.controller;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.app.budget.tracker.entity.Income;
import com.app.budget.tracker.model.IncomeDTO;
import com.app.budget.tracker.repository.IncomeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeController2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncomeRepository repository;

    @Autowired
    ObjectMapper mapper;

    @Test
    void addIncomeItem() {
    }

    @Test
    void getAllIncomeItems() throws Exception {
        Income income = new Income();
        income.setAmount(new BigDecimal(10));
        income.setCategory("Gift");
        income.setRecordDate(LocalDate.now());
        repository.save(income);
        MvcResult result = this.mockMvc.perform(get("/incomes"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                        .andReturn();
        // this uses a TypeReference to inform Jackson about the Lists's generic type
        List<IncomeDTO> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<IncomeDTO>>() {});
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