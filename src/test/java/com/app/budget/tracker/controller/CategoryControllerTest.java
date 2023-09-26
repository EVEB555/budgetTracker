package com.app.budget.tracker.controller;

import com.app.budget.tracker.entity.Category;
import com.app.budget.tracker.model.CategoryDTO;
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
    private ObjectMapper mapper;

    @Autowired
    private CategoryService categoryService;

   /* @Test
    void createCategory() throws Exception {
        String categoryName = "Test Category";

        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .param("description", categoryName))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the category was created
        CategoryDTO createdCategory = mapper.readValue(result.getResponse().getContentAsString(), CategoryDTO.class);
        assertNotNull(createdCategory);
        assertEquals(categoryName, createdCategory.getDescription());

        // Clean up: Delete the created category
        categoryService.deleteCategory(createdCategory.getId());
    }*/

    @Test
    void createCategory() throws Exception {
        String categoryName = "Test Category";

        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .param("description", categoryName))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the response contains the success message as plain text
        String responseContent = result.getResponse().getContentAsString();
        assertEquals("Category created successfully", responseContent);

        // Clean up: Delete the created category
        // You may need to modify this part based on how you handle category deletion
        // since the controller method currently does not return the created category ID.
    }


    @Test
    void getAllCategories() throws Exception {
        // Create some test categories
        categoryService.createCategory("Category 1");
        categoryService.createCategory("Category 2");

        MvcResult result = mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the response contains a list of categories
        List<CategoryDTO> categories = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CategoryDTO>>() {});
        assertTrue(categories.size() >= 2);

        // Clean up: Delete the test categories
        for (CategoryDTO category : categories) {
            categoryService.deleteCategory(category.getId());
        }
    }

    @Test
    void editCategory() throws Exception {
        // Create a test category
        Category category = categoryService.createCategory("Test Category");

        String updatedDescription = "Updated Test Category";

        MvcResult result = mockMvc.perform(
                        put("/categories/{categoryId}", category.getId())
                                .param("description", updatedDescription))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the category was updated
        CategoryDTO updatedCategory = categoryService.getAllCategories().stream()
                .filter(c -> c.getId().equals(category.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedCategory);
        assertEquals(updatedDescription, updatedCategory.getDescription());

        // Clean up: Delete the test category
        categoryService.deleteCategory(category.getId());
    }

    @Test
    void deleteCategory() throws Exception {
        // Create a test category
        Category category = categoryService.createCategory("Test Category");

        mockMvc.perform(
                        delete("/categories/{categoryId}", category.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        // Verify that the category was deleted
        CategoryDTO deletedCategory = categoryService.getAllCategories().stream()
                .filter(c -> c.getId().equals(category.getId()))
                .findFirst()
                .orElse(null);
        assertNull(deletedCategory);
    }
}

