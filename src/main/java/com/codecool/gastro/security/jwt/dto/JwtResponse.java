package com.codecool.gastro.security.jwt.dto;

import com.codecool.gastro.dto.role.RoleDto;

import java.util.Set;
import java.util.UUID;

public record JwtResponse(
        String token,
        String type,
        String refreshToken,
        UUID customerId,
        String email,
        Set<RoleDto> roles
) {
}
