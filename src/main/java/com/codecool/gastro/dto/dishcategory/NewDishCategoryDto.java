package com.codecool.gastro.dto.dishcategory;

import com.codecool.gastro.dto.NewEntityDto;
import com.codecool.gastro.repository.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewDishCategoryDto(
        @NotBlank(message = "Category cannot be empty")
        @Pattern(regexp = Ingredient.REGEX_FOR_INGREDIENT,
                message = "Category must contain only letters and not start with number or whitespace")
        String category
) implements NewEntityDto {
}
