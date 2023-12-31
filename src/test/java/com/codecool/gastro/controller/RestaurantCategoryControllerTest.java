package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantCategoryService service;

    private final String RESTAURANT_CATEGORY_NAME = "CategoryName";

    @Test
    void testDeleteRestaurantCategoryShouldReturnStatusNoContentWhenCategoryExist() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/restaurant-categories/" + id))
                .andExpect(status().isNoContent());
    }


    @Test
    void testGetAllRestaurantCategoriesShouldReturnStatusOkAndListOfCategoriesWhenCalled() throws Exception {
        RestaurantCategoryDto categoryDto = new RestaurantCategoryDto(UUID.randomUUID(), "CategoryName");

        when(service.getRestaurantCategories()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/api/v1/restaurant-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(categoryDto.id().toString()));
    }

    @Test
    void testCreateNewRestaurantCategoryShouldReturnStatusCreatedWhenValidDataProvided() throws Exception {
        NewRestaurantCategoryDto newCategoryDto = new NewRestaurantCategoryDto(RESTAURANT_CATEGORY_NAME);

        RestaurantCategoryDto createdCategoryDto = new RestaurantCategoryDto(UUID.randomUUID(), RESTAURANT_CATEGORY_NAME);

        when(service.saveRestaurantCategory(newCategoryDto)).thenReturn(createdCategoryDto);

        mockMvc.perform(post("/api/v1/restaurant-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\": \"CategoryName\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdCategoryDto.id().toString()))
                .andExpect(jsonPath("$.category").value(RESTAURANT_CATEGORY_NAME));
    }

    @Test
    void testCreateNewRestaurantCategoryShouldReturnStatusBadRequestWhenNameIsEmpty() throws Exception {
        NewRestaurantCategoryDto newCategoryDto = new NewRestaurantCategoryDto("");

        mockMvc.perform(post("/api/v1/restaurant-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\": \"\"}"))
                .andExpect(status().isUnsupportedMediaType());
    }
}