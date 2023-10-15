package com.codecool.gastro.dto.restaurantcategory;

import com.codecool.gastro.dto.NewEntityDto;
import jakarta.validation.constraints.NotBlank;

public record NewRestaurantCategoryDto(
        @NotBlank(message = "Category cannot be empty")
        String category
) implements NewEntityDto {
}