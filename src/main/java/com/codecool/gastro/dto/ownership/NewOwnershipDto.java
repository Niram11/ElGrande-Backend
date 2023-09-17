package com.codecool.gastro.dto.ownership;

import com.codecool.gastro.controller.validation.CustomerExist;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import jakarta.persistence.Id;

import java.util.Set;
import java.util.UUID;

public record NewOwnershipDto(
        @CustomerExist
        UUID customerId,
        Set<RestaurantDto> restaurants
) {
}
