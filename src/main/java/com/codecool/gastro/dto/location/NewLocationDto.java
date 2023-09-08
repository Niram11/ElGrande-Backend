package com.codecool.gastro.dto.location;

import java.math.BigDecimal;
import java.util.UUID;

public record NewLocationDto(
        BigDecimal latitude,
        BigDecimal longitude
) {}
