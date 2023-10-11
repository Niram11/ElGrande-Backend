package com.codecool.gastro.dto.auth;

import java.util.UUID;

public record JwtResponse(
        String token,
        String type,
        UUID customerId,
        String email
) {
}
