package com.codecool.gastro.dto.ownership;

import com.codecool.gastro.dto.restaurant.RestaurantDto;

import java.util.Set;
import java.util.UUID;

public record OwnershipDto(
        UUID id,
        Set<RestaurantDto> restaurants
) {
}
