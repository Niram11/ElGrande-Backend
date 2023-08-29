package com.codecool.gastro.dto.businesshour;

import java.time.LocalTime;
import java.util.UUID;

public record NewBusinessHourDto(
        Integer dayOfWeek,
        LocalTime openingHour,
        LocalTime closingHour,
        UUID restaurantId

) {

}
