package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantCategoryService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    void testGetRestaurantCategoryByIdShouldReturnStatusNotFoundWhenNoCategory() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.getRestaurantCategory(id)).thenThrow(new ObjectNotFoundException(id, RestaurantCategoryDto.class));

        mockMvc.perform(get("/api/v1/restaurant-categories/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Object of class RestaurantCategoryDto and id " + id + " cannot be found"));
    }

    @Test
    void testGetRestaurantCategoryByIdShouldReturnStatusOkAndCategoryDtoWhenCategoryExist() throws Exception {
        RestaurantCategoryDto categoryDto = new RestaurantCategoryDto(UUID.randomUUID(), "CategoryName");

        when(service.getRestaurantCategory(categoryDto.id())).thenReturn(categoryDto);

        mockMvc.perform(get("/api/v1/restaurant-categories/" + categoryDto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryDto.id().toString()));
    }

    @Test
    void testDeleteRestaurantCategoryShouldReturnStatusNoContentWhenCategoryExist() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/restaurant-categories/" + id))
                .andExpect(status().isNoContent());
    }
}