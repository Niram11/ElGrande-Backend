package com.codecool.gastro.dto.restaurantcategory;

import jakarta.validation.constraints.NotBlank;

public record NewRestaurantCategoryDto(
        @NotBlank(message = "Category cannot be empty")
        String category
) {
}