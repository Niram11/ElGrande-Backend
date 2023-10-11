package com.codecool.gastro.dto.dishcategory;

import java.util.UUID;

public record DishCategoryDto(
        UUID id,
        String category
) {
}
