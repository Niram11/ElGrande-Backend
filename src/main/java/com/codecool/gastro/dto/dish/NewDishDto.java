package com.codecool.gastro.dto.dish;

import com.codecool.gastro.controller.validation.RestaurantExist;
import com.codecool.gastro.repository.entity.Dish;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record NewDishDto(
        @NotBlank(message = "Dish name cannot be empty")
        @Pattern(regexp = Dish.REGEX_FOR_DISH,
                message = "Dish name must contain only letters and not start with number or whitespace")
        String dishName,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive number")
        BigDecimal price,
        @RestaurantExist
        UUID restaurantId

) {
}
