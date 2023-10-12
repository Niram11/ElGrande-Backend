package com.codecool.gastro.dto.customer;

import com.codecool.gastro.repository.entity.Role;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        LocalDate submissionTime,
        Set<Role> roles
) {
}
