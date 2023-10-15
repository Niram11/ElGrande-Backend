package com.codecool.gastro.security.jwt.dto;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken,
        String type
) {
}
