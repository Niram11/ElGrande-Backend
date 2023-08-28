package com.codecool.gastro.controler.dto.restaurant;

import java.util.UUID;

public record NewRestaurantDTO(
        String name,
        String description,
        String website,
        String contactNumber,
        String contactEmail
) {

}
