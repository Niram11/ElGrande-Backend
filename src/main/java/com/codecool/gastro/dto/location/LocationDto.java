package com.codecool.gastro.dto.location;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record LocationDto(
        UUID id,
        BigDecimal latitude,
        BigDecimal longitude,
        Set<RestaurantDto> restaurants
) {
}