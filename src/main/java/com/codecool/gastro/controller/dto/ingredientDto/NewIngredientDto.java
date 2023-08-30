package com.codecool.gastro.controller.dto.ingredientDto;

import jakarta.validation.constraints.NotBlank;

public record NewIngredientDto(
        @NotBlank
        String name
) {
}
