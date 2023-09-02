package com.codecool.gastro.dto.maplocation;

import java.math.BigDecimal;
import java.util.UUID;

public record MapLocationDto(
        UUID id,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
