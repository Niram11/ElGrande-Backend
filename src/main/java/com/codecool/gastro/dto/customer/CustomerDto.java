package com.codecool.gastro.dto.customer;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        LocalDate submissionTime,
        String passwordHash,
        Set<RestaurantDto> restaurants
) {
}
