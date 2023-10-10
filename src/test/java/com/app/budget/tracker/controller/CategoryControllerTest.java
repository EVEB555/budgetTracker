package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.model.CategoryDTO;
import com.app.budget.tracker.repository.CategoryRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void addCategory() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest("Groceries");

        mockMvc.perform(
                        post("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        List<Category> categories = categoryRepository.findAll();
        assertEquals(1, categories.size());
        assertEquals("Groceries", categories.get(0).getDescription());

        categoryRepository.deleteAll();
    }

    @Test
    void getAllCategories() throws Exception {
        Category category = new Category();
        category.setDescription("Shopping");
        categoryRepository.save(category);

        MvcResult result = mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDTO> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CategoryDTO>>() {});

        assertEquals(1, actual.size());
        assertEquals("Shopping", actual.get(0).getDescription());

        categoryRepository.deleteAll();
    }

    @Test
    void editCategory() throws Exception {
        Category category = new Category();
        category.setDescription("Shopping");
        category = categoryRepository.save(category);

        EditCategoryRequest editRequest = new EditCategoryRequest("Updated Shopping");

        MvcResult result = mockMvc.perform(
                        put("/categories/{categoryId}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(editRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Category editedCategory = categoryRepository.findById(category.getId()).orElse(null);
        assertNotNull(editedCategory);
        assertEquals("Updated Shopping", editedCategory.getDescription());

        categoryRepository.deleteAll();
    }

    @Test
    void deleteCategory() throws Exception {
        Category category = new Category();
        category.setDescription("Shopping");
        category = categoryRepository.save(category);

        mockMvc.perform(
                        delete("/categories/{categoryId}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(categoryRepository.existsById(category.getId()));
    }
}

