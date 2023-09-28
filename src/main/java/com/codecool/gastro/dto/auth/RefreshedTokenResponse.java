package com.codecool.gastro.dto.auth;

public record RefreshedTokenResponse(
        String refreshedToken,
        String type
) {
}
