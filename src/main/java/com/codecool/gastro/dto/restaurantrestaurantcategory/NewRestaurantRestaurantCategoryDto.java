package com.codecool.gastro.dto.restaurantrestaurantcategory;

import java.util.UUID;

public record NewRestaurantRestaurantCategoryDto(
        UUID restaurantId,
        UUID categoryId
) {
}