package com.codecool.gastro.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewRestaurantMenuDto(
        @NotBlank
        String dishName,
        @NotBlank
        String ingredients,
        @NotNull
        BigDecimal price

) {
}
