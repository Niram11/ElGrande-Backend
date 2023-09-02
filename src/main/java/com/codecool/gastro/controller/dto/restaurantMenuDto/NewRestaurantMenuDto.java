package com.codecool.gastro.controller.dto.restaurantMenuDto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record NewRestaurantMenuDto(
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 3, message = "Field must have at least 3 characters")
        @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$",
                message = "Field must contain only letters and not start with number or whitespace")
        String dishName,
        @NotNull
        @Positive(message = "Price must be a positive number")
        BigDecimal price

) {
}
