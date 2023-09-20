package com.codecool.gastro.dto.customer;


import java.time.LocalDate;
import java.util.UUID;

public record DetailedCustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        LocalDate submissionTime,
        UUID[] restaurants,
        UUID ownershipId
) {
}
