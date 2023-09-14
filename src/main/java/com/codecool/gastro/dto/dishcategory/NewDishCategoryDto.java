package com.codecool.gastro.dto.dishcategory;

import jakarta.validation.constraints.NotBlank;

public record NewDishCategoryDto(
        @NotBlank(message = "Dish category cannot be empty")
        String category
) {
}
