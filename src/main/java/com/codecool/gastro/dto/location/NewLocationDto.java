package com.codecool.gastro.dto.location;

import com.codecool.gastro.dto.restaurant.RestaurantDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record NewLocationDto(
        BigDecimal latitude,
        BigDecimal longitude,

        Set<RestaurantDto> restaurants
) {}
