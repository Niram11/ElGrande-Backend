package com.codecool.gastro.dto.promotedlocal;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.UUID;

public record NewPromotedLocalDto(
        @NotBlank(message = "Start date cannot be empty")
        LocalTime startDate,
        @NotBlank(message = "End date cannot be empty")
        LocalTime endDate,
        @NotBlank(message = "Invalid id")
        UUID restaurantId
) {

}