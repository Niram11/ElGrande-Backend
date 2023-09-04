package com.codecool.gastro.DTO.restaurantrestaurantcategory;

import java.util.UUID;

public record NewRestaurantRestaurantCategoryDTO(
        UUID restaurantId,
        UUID categoryId
) {
}
