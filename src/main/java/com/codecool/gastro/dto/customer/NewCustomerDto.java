package com.codecool.gastro.dto.customer;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record NewCustomerDto(
        @NotBlank
        @Size(min = 3)
        String name,

        @NotBlank
        @Size(min = 3)
        String surname,

        @Email
        String email,

        @NotBlank
        String passwordHash,
        Set<RestaurantDto> restaurants
) {
}
