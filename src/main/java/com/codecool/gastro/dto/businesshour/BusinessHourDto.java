package com.codecool.gastro.dto.businesshour;

import java.time.LocalTime;
import java.util.UUID;

public record BusinessHourDto(
        UUID id,
        Integer dayOfWeek,
        LocalTime openingHour,
        LocalTime closingHour
) {

}
