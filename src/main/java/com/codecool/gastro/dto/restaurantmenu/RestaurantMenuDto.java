package com.codecool.gastro.dto.restaurantmenu;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.dishcategory.DishCategoryDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record RestaurantMenuDto(
        UUID id,
        String dishName,
        BigDecimal price,
        UUID restaurantId,
        Set<IngredientDto> ingredients,
        Set<DishCategoryDto> categories
) {
}
