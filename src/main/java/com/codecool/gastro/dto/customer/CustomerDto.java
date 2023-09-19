package com.codecool.gastro.dto.customer;

import com.codecool.gastro.dto.restaurant.RestaurantDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        LocalDate submissionTime,
        String passwordHash,
        Set<RestaurantDto> restaurants
) {
}
