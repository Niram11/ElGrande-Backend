package com.codecool.gastro.dto.restaurant;

import jakarta.validation.constraints.*;

public record NewRestaurantDto(

        @NotBlank(message = "Name cannot be empty")
        String name,
        @NotBlank(message = "Description cannot be empty")
        String description,
        String website,
        Integer contactNumber,
        @Email(message = "Invalid email")
        String contactEmail
) {

}
