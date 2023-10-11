package com.codecool.gastro.dto.customer;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        LocalDate submissionTime
) {
}
