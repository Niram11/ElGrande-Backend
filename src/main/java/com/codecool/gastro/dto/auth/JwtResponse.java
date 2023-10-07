package com.codecool.gastro.dto.auth;

import java.util.UUID;

public record JwtResponse(
        String token,
        UUID id,
        String name,
        String email

) {
}
