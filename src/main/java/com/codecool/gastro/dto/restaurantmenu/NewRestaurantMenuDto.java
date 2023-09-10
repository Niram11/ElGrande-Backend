package com.codecool.gastro.dto.restaurantmenu;

import com.codecool.gastro.repository.entity.RestaurantMenu;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record NewRestaurantMenuDto(
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 3, message = "Field must have at least 3 characters")
        @Pattern(regexp = RestaurantMenu.REGEX_FOR_MENU,
                message = "Field must contain only letters and not start with number or whitespace")
        String dishName,
        @NotNull(message = "Price cannot be empty")
        @Positive(message = "Price must be a positive number")
        BigDecimal price,
        UUID restaurantId

) {
}
