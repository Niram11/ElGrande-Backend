package com.codecool.gastro.dto.businesshour;

import com.codecool.gastro.controller.validation.RestaurantExist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record NewBusinessHourDto(
        @Min(value = 1, message = "Day of week must be greater then or equal 1")
        @Max(value = 7, message = "Day of week must be less then or equal 7")
        @NotNull(message = "Day of week cannot be null")
        Integer dayOfWeek,
        @NotBlank(message = "Opening hour cannot be empty")
        LocalTime openingHour,
        @NotBlank(message = "Closing hour cannot be empty")
        LocalTime closingHour,
        @RestaurantExist
        UUID restaurantId

) {

}
