package com.codecool.gastro.dto.businesshour;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.UUID;

public record NewBusinessHourDto(
        @Min(value = 1, message = "Number must be greater then or equal 1")
        @Max(value = 7, message = "Number must be less then or equal 10")
        Integer dayOfWeek,
        LocalTime openingHour,
        LocalTime closingHour,
        UUID restaurantId

) {

}
