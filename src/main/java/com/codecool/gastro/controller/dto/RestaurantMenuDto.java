package com.codecool.gastro.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RestaurantMenuDto(
        UUID uuid,
        String dishName,
        String ingredients,
        BigDecimal price
) {
}
