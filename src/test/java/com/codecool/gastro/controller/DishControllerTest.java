package com.codecool.gastro.controller;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.DishService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DishController.class)
public class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishService service;

    @MockBean
    private RestaurantRepository restaurantRepository;

    private UUID dishId;
    private UUID restaurantId;
    private DishDto dishDto;
    private String contentResponse;

    @BeforeEach
    void setUp() {
        dishId = UUID.fromString("aefdc6b1-4341-4d67-8f2f-75518085f115");
        restaurantId = UUID.fromString("cc3ce018-4329-4309-8558-4fd2b5a3cf4a");

        dishDto = new DishDto(
                dishId,
                "DishName",
                BigDecimal.valueOf(12.3),
                Set.of(),
                Set.of()
        );

        contentResponse = """
                {
                    "id": "aefdc6b1-4341-4d67-8f2f-75518085f115",
                    "dishName": "DishName",
                    "price": 12.3,
                    "ingredients": [],
                    "categories": []
                }
                """;
    }

    @Test
    void testGetDishes_ShouldReturnStatusOkAndListOfDishes_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/dishes"))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetDishById_ShouldReturnStatusOkAndDishDto_WhenExist() throws Exception {
        // when
        when(service.getDishById(dishId)).thenReturn(dishDto);

        // then
        mockMvc.perform(get("/api/v1/dishes/" + dishId))
                .andExpectAll(status().isOk(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testGetDishById_ShouldReturnStatusNotFoundAndErrorMessage_WhenNoDish() throws Exception {
        // when
        when(service.getDishById(dishId)).thenThrow(new ObjectNotFoundException(dishId, Dish.class));

        // then
        mockMvc.perform(get("/api/v1/dishes/" + dishId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Dish and id " + dishId + " cannot be found")
                );
    }

    @Test
    void testGetDishesByRestaurant_ShouldReturnStatusOkAndList_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/dishes?restaurantId=" + restaurantId))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testCreateNewDish_ShouldReturnStatusCreatedAndDishDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "dishName": "DishName",
                    "price": 12.3,
                    "restaurantId": "cc3ce018-4329-4309-8558-4fd2b5a3cf4a"
                }
                """;
        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.saveNewDish(any(NewDishDto.class))).thenReturn(dishDto);

        // then
        mockMvc.perform(post("/api/v1/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateNewDish_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues() throws Exception {
        // given
        String contentRequestOne = """
                {
                    "dishName": "",
                    "price": null,
                    "restaurantId": ""
                }
                """;

        String contentRequestTwo = """
                {
                    "dishName": "Name",
                    "price": -1,
                    "restaurantId": ""
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(post("/api/v1/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Dish name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Price cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Dish name must contain only letters and not start with number or whitespace")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );

        mockMvc.perform(post("/api/v1/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestTwo))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Price must be a positive number"))
                );
    }

    @Test
    void testUpdateDish_ShouldReturnStatusCreatedAndDishDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "dishName": "DishName",
                    "price": 12.3,
                    "restaurantId": "cc3ce018-4329-4309-8558-4fd2b5a3cf4a"
                }
                """;
        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.saveNewDish(any(NewDishDto.class))).thenReturn(dishDto);

        // then
        mockMvc.perform(post("/api/v1/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testUpdateDish_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues() throws Exception {
        // given
        String contentRequestOne = """
                {
                    "dishName": "",
                    "price": null,
                    "restaurantId": ""
                }
                """;

        String contentRequestTwo = """
                {
                    "dishName": "Name",
                    "price": -1,
                    "restaurantId": ""
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Dish name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Price cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Dish name must contain only letters and not start with number or whitespace")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );

        mockMvc.perform(put("/api/v1/dishes/" + dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestTwo))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Price must be a positive number"))
                );
    }

    @Test
    void testAssignIngredientToDish_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId + "/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAssignDishCategoryToDish_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId + "/dish-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDish_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(delete("/api/v1/dishes/" + dishId))
                .andExpect(status().isNoContent());
    }


}