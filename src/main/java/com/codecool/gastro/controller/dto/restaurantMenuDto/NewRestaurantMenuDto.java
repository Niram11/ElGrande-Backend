package com.codecool.gastro.controller.dto.restaurantMenuDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewRestaurantMenuDto(
        //bardziej szczególowa walidacja
        @NotBlank
        String dishName,
        @NotNull
        BigDecimal price

) {
}
