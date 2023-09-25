package com.codecool.gastro.controller;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.service.DishCategoryService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DishCategoryController.class)
public class DishCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishCategoryService service;

    private UUID dishCategoryId;

    private DishCategoryDto dishCategoryDto;
    private NewDishCategoryDto newDishCategoryDto;

    private String contentResponse;

    @BeforeEach
    void setUp() {
        dishCategoryId = UUID.fromString("281c70ec-e4a8-4b5d-bf1d-596766388994");

        dishCategoryDto = new DishCategoryDto(dishCategoryId, "Category");

        newDishCategoryDto = new NewDishCategoryDto("Category");

        contentResponse = """
                {
                    "id": "281c70ec-e4a8-4b5d-bf1d-596766388994",
                    "category": "Category"
                }
                """;
    }

    @Test
    void testGetCategories_ShouldReturnStatusOkAndListOfCategories_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/dish-categories"))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetDishCategoryById_ShouldReturnStatusOkAndDishCategoryDto_WhenExist() throws Exception {
        // when
        when(service.getDishCategoryById(dishCategoryId)).thenReturn(dishCategoryDto);

        // then
        mockMvc.perform(get("/api/v1/dish-categories/" + dishCategoryId))
                .andExpectAll(status().isOk(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testGetDishCategoryById_ShouldReturnStatusNotFound_WhenNoDishCategory() throws Exception {
        // when
        when(service.getDishCategoryById(dishCategoryId)).thenThrow(new ObjectNotFoundException(dishCategoryId, DishCategory.class));

        // then
        mockMvc.perform(get("/api/v1/dish-categories/" + dishCategoryId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class DishCategory and id " + dishCategoryId + " cannot be found"));
    }

    @Test
    void testCreateDishCategory_ShouldReturnStatusCreatedAndDishCategoryDto_WhenProvidingValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "category": "Category"
                }
                """;

        // when
        when(service.saveDishCategory(newDishCategoryDto)).thenReturn(dishCategoryDto);

        // then
        mockMvc.perform(post("/api/v1/dish-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateDishCategory_ShouldReturnStatusBadRequestAndErrorMessages_WhenProvidingInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "category": ""
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/dish-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage").value("Dish category cannot be empty")
                );
    }

    @Test
    void testUpdateDishCategory_ShouldReturnStatusCreatedAndDishCategoryDto_WhenProvidingValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "category": "Category"
                }
                """;

        // when
        when(service.updateDishCategory(dishCategoryId, newDishCategoryDto)).thenReturn(dishCategoryDto);

        // then
        mockMvc.perform(put("/api/v1/dish-categories/" + dishCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testUpdateDishCategory_ShouldReturnStatusBadRequestAndErrorMessages_WhenProvidingInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "category": ""
                }
                """;

        // then
        mockMvc.perform(put("/api/v1/dish-categories/" + dishCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage").value("Dish category cannot be empty")
                );
    }


    @Test
    void testDeleteDishCategory_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // test
        mockMvc.perform(delete("/api/v1/dish-categories/" + dishCategoryId))
                .andExpectAll(status().isNoContent());
    }


}