package com.codecool.gastro.controller.dto.restaurantMenuDto;

import com.codecool.gastro.controller.dto.IdNamePairDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RestaurantMenuDto(
        UUID uuid,
        String dishName,
        BigDecimal price,
        List<IdNamePairDto> ingredients) {
}
