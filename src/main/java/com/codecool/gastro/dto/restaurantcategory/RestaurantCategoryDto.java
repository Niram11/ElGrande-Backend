package com.codecool.gastro.dto.restaurantcategory;

import java.util.UUID;

public record RestaurantCategoryDto(
        UUID id,
        String category
){
}