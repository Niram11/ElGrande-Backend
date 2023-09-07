package com.codecool.gastro.dto.ownership;

import jakarta.persistence.Id;

import java.util.Set;
import java.util.UUID;

public record OwnershipDto(
        UUID id,
        UUID customerId,
        Set<UUID> restaurantsIdsSet
)
{
}
