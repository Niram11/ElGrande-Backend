package com.codecool.gastro.dto.restaurant;

import java.util.UUID;

public record RestaurantDTO(
        UUID id,
        String name,
        String description,
        String website,
        String contactNumber,
        String contactEmail
) {

}
