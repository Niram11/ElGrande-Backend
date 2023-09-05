package com.codecool.gastro.dto.ingredient;

import java.util.UUID;

public record IngredientDto(
        UUID id,
        String name
) {
}
