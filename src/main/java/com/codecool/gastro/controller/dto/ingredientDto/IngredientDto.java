package com.codecool.gastro.controller.dto.ingredientDto;

import java.util.UUID;

public record IngredientDto(
        UUID id,
        String name
) {
}
