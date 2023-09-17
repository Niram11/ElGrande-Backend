package com.codecool.gastro.dto.location;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record NewLocationDto(
        @NotNull(message = "Latitude cannot be null")
        BigDecimal latitude,
        @NotNull(message = "Longitude cannot be null")
        BigDecimal longitude
) {
}
