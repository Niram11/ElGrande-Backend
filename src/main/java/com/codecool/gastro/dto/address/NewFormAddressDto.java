package com.codecool.gastro.dto.address;

import jakarta.validation.constraints.NotBlank;

public record NewFormAddressDto(
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
        String additionalDetails
) {
}
