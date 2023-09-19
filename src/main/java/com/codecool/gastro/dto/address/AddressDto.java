package com.codecool.gastro.dto.address;

import java.util.UUID;

public record AddressDto(
        UUID id,
        String country,
        String city,
        String postalCode,
        String street,
        String streetNumber,
        String additionalDetails
) {
}
