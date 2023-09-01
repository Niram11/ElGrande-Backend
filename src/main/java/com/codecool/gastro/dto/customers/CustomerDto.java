package com.codecool.gastro.dto.customers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CustomerDto(
        @NotBlank
        UUID id,
        @NotBlank
        String forename,
        @NotBlank
        String surname,
        @Email
        String email,
        @NotBlank
        String passwordHash
)
{
}
