package com.codecool.gastro.dto.customer;

import com.codecool.gastro.controller.validation.Unoccupied;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record NewCustomerDto(
        @NotBlank(message = "Name cannot be empty")
        String name,
        String surname,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email")
        @Unoccupied
        String email,

        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
