package com.codecool.gastro.DTO.restaurantrestaurantcategory;

import java.util.UUID;

public record RestaurantRestaurantCategoryDTO(
        UUID restaurantId,
        UUID categoryId
) {
}
