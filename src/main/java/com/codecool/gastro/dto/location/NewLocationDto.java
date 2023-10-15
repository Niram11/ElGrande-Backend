package com.codecool.gastro.dto.location;

import com.codecool.gastro.dto.NewEntityDto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewLocationDto(
        @NotNull(message = "Latitude cannot be null")
        BigDecimal latitude,
        @NotNull(message = "Longitude cannot be null")
        BigDecimal longitude
) implements NewEntityDto {
}
