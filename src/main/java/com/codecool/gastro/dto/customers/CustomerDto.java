package com.codecool.gastro.dto.customers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CustomerDto(

        UUID id,

        @NotBlank
        @Size(min=3)
        String forename,

        @NotBlank
        @Size(min=3)
        String surname,

        @Email
        @Size(min=6)
        String email,

        @NotBlank
        @Size(min=8)
        String passwordHash
)
{
}
