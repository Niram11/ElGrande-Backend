package com.codecool.gastro.security.jwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email can not be empty")
        @Email(message = "Invalid email")
        String email,
        @NotBlank(message = "Password can not be empty")
        String password
) {
}
