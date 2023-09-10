package com.codecool.gastro.dto.ownership;

import jakarta.persistence.Id;

import java.util.UUID;

public record NewOwnershipDto(
        UUID customerId
)
{

}
