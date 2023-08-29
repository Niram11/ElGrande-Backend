package com.codecool.gastro.controler.dto.businesshour;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

public record BusinessHourDTO(
        UUID id,
        Integer dayOfWeek,
        LocalTime openingHour,
        LocalTime closingHour,
        UUID restaurantId
) {

}
