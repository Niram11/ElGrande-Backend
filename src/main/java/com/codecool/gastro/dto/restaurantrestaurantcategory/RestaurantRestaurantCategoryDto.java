package com.codecool.gastro.dto.restaurantrestaurantcategory;

import java.util.UUID;

public record RestaurantRestaurantCategoryDto(
        UUID restaurantId,
        UUID categoryId
) {
}
