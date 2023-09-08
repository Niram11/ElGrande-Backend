package com.codecool.gastro.dto.address;

import java.util.UUID;

public record AddressDto(
        UUID id,
        String city,
        String street,
        String streetNumber,
        String additionalDetails
) {
}
