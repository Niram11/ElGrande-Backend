package com.codecool.gastro.dto.dish;

import com.codecool.gastro.repository.entity.Dish;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record NewDishDto(
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 3, message = "Field must have at least 3 characters")
        @Pattern(regexp = Dish.REGEX_FOR_MENU,
                message = "Field must contain only letters and not start with number or whitespace")
        String dishName,
        @NotNull(message = "Price cannot be empty")
        @Positive(message = "Price must be a positive number")
        BigDecimal price,
        UUID restaurantId

) {
}
