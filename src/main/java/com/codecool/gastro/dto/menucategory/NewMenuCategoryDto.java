package com.codecool.gastro.dto.menucategory;

import jakarta.validation.constraints.NotBlank;

public record NewMenuCategoryDto(
        @NotBlank(message = "Menu category cannot be empty")
        String category
) {
}
