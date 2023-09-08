package com.codecool.gastro.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
        String passwordHash
) {
}
