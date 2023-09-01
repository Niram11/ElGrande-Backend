package com.codecool.gastro.dto.businesshour;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.UUID;

public record NewBusinessHourDto(
        @Min(1)
        @Max(7)
        Integer dayOfWeek,
        @NotBlank(message = "Opening hour cannot be empty")
        LocalTime openingHour,
        @NotBlank(message = "Closing hour cannot be empty")
        LocalTime closingHour,
        @NotBlank(message = "Invalid id")
        UUID restaurantId

) {

}
