package com.codecool.gastro.dto.ownership;

import java.util.List;
import java.util.UUID;

public record EditOwnershipDto(
        List<UUID> restaurantsId
) {
}
