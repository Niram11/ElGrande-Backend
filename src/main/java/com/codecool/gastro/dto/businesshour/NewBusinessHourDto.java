package com.codecool.gastro.dto.businesshour;

import com.codecool.gastro.controller.validation.RestaurantExist;
import com.codecool.gastro.controller.validation.TimeFormat;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.UUID;

public record NewBusinessHourDto(
        @Min(value = 1, message = "Day of week must be greater then or equal 1")
        @Max(value = 7, message = "Day of week must be less then or equal 7")
        @NotNull(message = "Day of week cannot be null")
        Integer dayOfWeek,
        @NotNull(message = "Opening hour cannot be empty")
        @TimeFormat
        LocalTime openingHour,
        @NotNull(message = "Closing hour cannot be empty")
        @TimeFormat
        LocalTime closingHour,
        @RestaurantExist
        UUID restaurantId

) {

}
