package com.codecool.gastro.dto.customers;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String forename,
        String surname,
        String email,
        String passwordHash
)
{
}
