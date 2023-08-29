package com.codecool.gastro.dto.restaurant;

public record NewRestaurantDTO(
        String name,
        String description,
        String website,
        String contactNumber,
        String contactEmail
) {

}
