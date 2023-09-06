package com.codecool.gastro.dto.customer;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CustomerDto(
        @Id
        UUID id,

        @NotBlank(message = "forename cannot be empty")
        @Size(min=3)
        String forename,

        @NotBlank(message = "surname cannot be empty")
        @Size(min=3)
        String surname,

        @Email
        String email,

        @NotBlank
        String passwordHash
)
{
}
