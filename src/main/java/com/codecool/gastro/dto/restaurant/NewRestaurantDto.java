package com.codecool.gastro.dto.restaurant;

public record NewRestaurantDto(
        String name,
        String description,
        String website,
        String contactNumber,
        String contactEmail
) {

}
