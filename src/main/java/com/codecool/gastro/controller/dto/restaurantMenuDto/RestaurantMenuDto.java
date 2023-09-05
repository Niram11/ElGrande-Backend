package com.codecool.gastro.controller.dto.restaurantMenuDto;

import com.codecool.gastro.controller.dto.ingredientDto.IngredientDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record RestaurantMenuDto(
        UUID id,
        String dishName,
        BigDecimal price,
        Set<IngredientDto> ingredients) {
}
