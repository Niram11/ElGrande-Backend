package com.codecool.gastro.dto.businesshour;

import com.codecool.gastro.controller.validation.RestaurantExist;
import com.codecool.gastro.controller.validation.TimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record NewFormBusinessHourDto(
        @Min(value = 1, message = "Day of week must be greater then or equal 1")
        @Max(value = 7, message = "Day of week must be less then or equal 7")
        @NotNull(message = "Day of week cannot be null")
        Integer dayOfWeek,
        @NotNull(message = "Opening hour cannot be null")
        @TimeFormat
        @Schema(type = "String", pattern = "HH:mm")
        LocalTime openingHour,
        @NotNull(message = "Closing hour cannot be null")
        @TimeFormat
        @Schema(type = "String", pattern = "HH:mm")
        LocalTime closingHour

) {

}
