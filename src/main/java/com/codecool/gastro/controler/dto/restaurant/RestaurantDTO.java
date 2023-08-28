package com.codecool.gastro.controler.dto.restaurant;

import java.rmi.server.UID;
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
