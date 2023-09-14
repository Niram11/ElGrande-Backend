package com.codecool.gastro.dto.promotedlocal;

import java.time.LocalTime;
import java.util.UUID;

public record PromotedLocalDto(
        UUID id,
        LocalTime startDate,
        LocalTime endDate
) {

}
