package com.codecool.gastro.security.jwt.dto;

import com.codecool.gastro.repository.entity.Role;

import java.util.Set;
import java.util.UUID;

public record JwtResponse(
        String token,
        String type,
        String refreshToken,
        UUID customerId,
        String email,
        Set<String> roles
) {
}
