package com.codecool.gastro.dto.restaurant;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewRestaurantDto(

        @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters long")
        String name,
        @NotBlank(message = "Description cannot be empty")
        String description,
        String website,
        @Digits(message = "Number must be 15 digits long", integer = 15, fraction = 0)
        Integer contactNumber,
        @Email(message = "Invalid email")
        String contactEmail
) {

}
