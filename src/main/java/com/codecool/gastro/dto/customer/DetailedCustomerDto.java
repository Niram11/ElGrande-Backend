package com.codecool.gastro.dto.customer;

import com.codecool.gastro.dto.restaurant.RestaurantDto;

import java.util.Set;
import java.util.UUID;

public record DetailedCustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        UUID[] restaurants,
        UUID ownershipId
) {
}
