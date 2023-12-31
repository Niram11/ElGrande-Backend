package com.codecool.gastro.dto.restaurant;

import java.util.UUID;

public record RestaurantDto(
        UUID id,
        String name,
        String description,
        String website,
        Integer contactNumber,
        String contactEmail
) {

}
