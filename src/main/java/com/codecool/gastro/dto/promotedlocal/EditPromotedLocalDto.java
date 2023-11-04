package com.codecool.gastro.dto.promotedlocal;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record EditPromotedLocalDto(
        @NotNull(message = "End date cannot be empty")
        LocalTime endDate
) {
}
