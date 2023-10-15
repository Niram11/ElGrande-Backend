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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DishController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DishControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    DishService service;
    @MockBean
    RestaurantRepository restaurantRepository;

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
                        jsonPath("$.errorMessage").value("Object of class "
                                + Dish.class.getSimpleName() + " and id " + dishId + " cannot be found")
                );
    }

    @Test
    void testGetDishesByRestaurant_ShouldReturnStatusOkAndList_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/dishes")
                        .param("restaurantId", String.valueOf(restaurantId)))
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
                    "price": 12.3
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


    @ParameterizedTest
    @MethodSource("getArgsForDishValidation")
    void testCreateNewDish_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues(
            String dishName, String price, String dishNameErrMsg, String priceErrMsg) throws Exception {
        // given
        String contentRequestOne = """
                {
                    "dishName": "%s",
                    "price": "%s"
                }
                """.formatted(dishName, price);


        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(post("/api/v1/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString(dishNameErrMsg)),
                        jsonPath("$.errorMessage", Matchers.containsString(priceErrMsg))
                );
    }

    @Test
    void testUpdateDish_ShouldReturnStatusCreatedAndDishDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "dishName": "DishName",
                    "price": 12.3
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

    @ParameterizedTest
    @MethodSource("getArgsForDishValidation")
    void testUpdateDish_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues(
            String dishName, String price, String dishNameErrMsg, String priceErrMsg) throws Exception {
        // given
        String contentRequestOne = """
                {
                    "dishName": "%s",
                    "price": "%s"
                }
                """.formatted(dishName, price);

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequestOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString(dishNameErrMsg)),
                        jsonPath("$.errorMessage", Matchers.containsString(priceErrMsg))
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
    void testAssignIngredientToDish_ShouldReturnStatusNotFound_WhenNoDish() throws Exception {
        // when
        doThrow(new ObjectNotFoundException(dishId, Dish.class))
                .when(service).assignIngredientToDish(dishId, Set.of());

        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId + "/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class "
                                + Dish.class.getSimpleName() + " and id " + dishId + " cannot be found")
                );
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
    void testAssignDishCategoryToDish_ShouldReturnStatusNotFound_WhenNoDish() throws Exception {
        // when
        doThrow(new ObjectNotFoundException(dishId, Dish.class))
                .when(service).assignDishCategoryToDish(dishId, Set.of());

        // then
        mockMvc.perform(put("/api/v1/dishes/" + dishId + "/dish-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class "
                                + Dish.class.getSimpleName() + " and id " + dishId + " cannot be found")
                );
    }

    @Test
    void testDeleteDish_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(delete("/api/v1/dishes/" + dishId))
                .andExpect(status().isNoContent());
    }

    private static Stream<Arguments> getArgsForDishValidation() {
        return Stream.of(
                Arguments.of("", "", "Dish name cannot be empty", "Price cannot be null"),
                Arguments.of("1asd123/eqw", "-1",
                        "Dish name must contain only letters and not start with number or whitespace",
                        "Price must be a positive number")
        );
    }
}