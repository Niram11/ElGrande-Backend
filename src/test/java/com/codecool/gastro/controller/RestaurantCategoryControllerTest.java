package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantCategoryService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestaurantCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantCategoryService service;

    @Test
    void testDeleteRestaurantCategoryShouldReturnStatusNoContentWhenCategoryExist() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/restaurant-categories/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRestaurantCategoryShouldReturnStatusNotFoundWhenNoCategory() throws Exception {
        UUID id = UUID.randomUUID();

        doThrow(new ObjectNotFoundException(id, RestaurantCategoryDto.class)).when(service).deleteRestaurantCategory(id);

        mockMvc.perform(delete("/api/v1/restaurant-categories/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllRestaurantCategoriesShouldReturnStatusOkAndListOfCategories_WhenCalled() throws Exception {
        RestaurantCategoryDto categoryDto = new RestaurantCategoryDto(UUID.randomUUID(), "CategoryName");

        when(service.getRestaurantCategories()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/api/v1/restaurant-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(categoryDto.id().toString()));
    }
}