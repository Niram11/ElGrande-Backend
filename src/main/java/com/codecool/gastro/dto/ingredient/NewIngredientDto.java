package com.codecool.gastro.dto.ingredient;

import com.codecool.gastro.repository.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewIngredientDto(
        @NotBlank(message = "Name cannot be empty")
        @Pattern(regexp = Ingredient.REGEX_FOR_INGREDIENT,
                message = "Name must contain only letters and not start with number or whitespace")
        String name
) {
}
