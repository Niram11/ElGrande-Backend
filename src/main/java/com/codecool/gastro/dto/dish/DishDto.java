package com.codecool.gastro.dto.dish;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.dishcategory.DishCategoryDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record DishDto(
        UUID id,
        String dishName,
        BigDecimal price,
        Set<IngredientDto> ingredients,
        Set<DishCategoryDto> categories
) {
}
