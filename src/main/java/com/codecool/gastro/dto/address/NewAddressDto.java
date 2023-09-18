package com.codecool.gastro.dto.address;

import com.codecool.gastro.controller.validation.RestaurantExist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewAddressDto(
        @NotBlank(message = "Country cannot be empty")
        String country,
        @NotBlank(message = "City cannot be empty")
        String city,
        @NotBlank(message = "Postal code cannot be empty")
        String postalCode,
        @NotBlank(message = "Street cannot be empty")
        String street,
        @NotBlank(message = "Street number cannot be empty")
        String streetNumber,
        String additionalDetails,
        @RestaurantExist
        UUID restaurantId
) {
}
