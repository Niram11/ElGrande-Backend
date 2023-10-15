package com.codecool.gastro.security.jwt.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
        @NotBlank(message = "Token can not be empty")
        String token
) {
}
