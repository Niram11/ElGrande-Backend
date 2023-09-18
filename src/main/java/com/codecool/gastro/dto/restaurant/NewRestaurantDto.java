package com.codecool.gastro.dto.restaurant;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

import java.util.UUID;

public record NewRestaurantDto(

        @NotBlank(message = "Name cannot be empty")
        @Size(max = 100, message = "Name must be max 100 characters long")
        String name,
        @NotBlank(message = "Description cannot be empty")
        String description,
        String website,
        @Digits(integer = 9, fraction = 0, message = "Contact Number must be a 9-digit integer")
        Integer contactNumber,
        @Email(message = "Invalid email")
        String contactEmail
) {

}
