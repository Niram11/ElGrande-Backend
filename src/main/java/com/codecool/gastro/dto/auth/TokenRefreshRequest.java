package com.codecool.gastro.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
        @NotBlank(message = "Token can not be empty")
        String token
) {
}
