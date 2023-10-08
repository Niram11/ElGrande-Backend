package com.codecool.gastro.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record EditCustomerDto(
        @NotBlank(message = "Name cannot be empty")
        String name,
        @NotBlank(message = "Surname cannot be empty")
        String surname
) {
}
