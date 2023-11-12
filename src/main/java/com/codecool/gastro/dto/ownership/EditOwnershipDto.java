package com.codecool.gastro.dto.ownership;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record EditOwnershipDto(
        Set<UUID> restaurantsId
) {
}
