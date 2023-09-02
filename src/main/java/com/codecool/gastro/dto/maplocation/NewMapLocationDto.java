package com.codecool.gastro.dto.maplocation;

import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.UUID;

public record NewMapLocationDto(
        @Digits(message = "Max 4 number before \",\"", integer = 4, fraction = 10)
        BigDecimal latitude,
        @Digits(message = "Max 4 number before \",\"", integer = 4, fraction = 10)
        BigDecimal longitude
) {
}
