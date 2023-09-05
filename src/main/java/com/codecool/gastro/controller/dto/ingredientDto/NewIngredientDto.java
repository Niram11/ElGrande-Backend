package com.codecool.gastro.controller.dto.ingredientDto;

import com.codecool.gastro.repository.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewIngredientDto(
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 3, message = "Field must have at least 3 characters")
        @Pattern(regexp = Ingredient.REGEX_FOR_INGREDIENT,
                message = "Field must contain only letters and not start with number or whitespace")
        String name
) {
}
