package com.codecool.gastro.DTO.restaurantcategory;

import java.util.UUID;

public record RestaurantCategoryDTO (
        UUID id,
        String category
){
}
