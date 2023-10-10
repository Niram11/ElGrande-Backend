package com.codecool.gastro.dto.dish;

import com.codecool.gastro.repository.entity.Dish;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EditDishDto(
        @NotBlank(message = "Dish name cannot be empty")
        @Pattern(regexp = Dish.REGEX_FOR_DISH,
                message = "Dish name must contain only letters and not start with number or whitespace")
        String dishName,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive number")
        BigDecimal price
) {

}
