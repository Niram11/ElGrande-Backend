package com.codecool.gastro.dto.customer;

import com.codecool.gastro.controller.validation.UnoccupiedEmail;
import com.codecool.gastro.dto.NewEntityDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewCustomerDto(
        @NotBlank(message = "Name cannot be empty")
        String name,
        String surname,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email")
        @UnoccupiedEmail
        String email,

        @NotBlank(message = "Password cannot be empty")
        String password
) implements NewEntityDto {
}
