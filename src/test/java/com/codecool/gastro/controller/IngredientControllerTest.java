package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.IngredientService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IngredientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class IngredientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    IngredientService service;

    private UUID ingredientId;
    private IngredientDto ingredientDto;
    private String contentResponse;

    @BeforeEach
    void setUp() {
        ingredientId = UUID.fromString("7afcab8c-4d61-4f7d-b6c8-bc69b2dfed43");

        ingredientDto = new IngredientDto(
                ingredientId,
                "Tomato"
        );


        contentResponse = """
                {
                    "id": "7afcab8c-4d61-4f7d-b6c8-bc69b2dfed43",
                    "name": "Tomato"
                }
                """;
    }

    @Test
    void testGetAllIngredients_ShouldReturnStatusOkAndList_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/ingredients"))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testCreateIngredient_ShouldReturnStatusCreatedAndIngredientDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "name": "Tomato"
                }
                """;

        // when
        when(service.saveNewIngredient(any(NewIngredientDto.class))).thenReturn(ingredientDto);

        // then
        mockMvc.perform(post("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateIngredient_ShouldReturnStatusBadRequestAndErrorMessages_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "name": ""
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Name must contain only letters and not start with number or whitespace"))
                );
    }

    @Test
    void testDeleteIngredient_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(delete("/api/v1/ingredients/" + ingredientId))
                .andExpectAll(status().isNoContent());
    }
}