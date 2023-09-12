package com.codecool.gastro.dto.restaurant;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

import java.util.UUID;

public record NewRestaurantDto(

        @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters long")
        String name,
        @NotBlank(message = "Description cannot be empty")
        String description,
        String website,
        @Digits(integer = 9, fraction = 0, message = "Number must be 9 digits long")
        Integer contactNumber,
        @Email(message = "Invalid email")
        String contactEmail
) {

}
