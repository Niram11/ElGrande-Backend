package com.codecool.gastro.dto.customer;


import java.util.UUID;

public record DetailedCustomerDto(
        UUID id,
        String name,
        String surname,
        String email,
        UUID[] restaurants,
        UUID ownershipId
) {
}
