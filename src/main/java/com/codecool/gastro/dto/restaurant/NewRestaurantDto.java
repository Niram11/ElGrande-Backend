package com.codecool.gastro.dto.restaurant;

import com.codecool.gastro.dto.NewEntityDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewRestaurantDto(

        @NotBlank(message = "Name cannot be empty")
        String name,
        @NotBlank(message = "Description cannot be empty")
        String description,
        String website,
        Integer contactNumber,
        @Email(message = "Invalid email")
        String contactEmail
) implements NewEntityDto {

}
