package com.codecool.gastro.dto.promotedlocal;

import java.time.LocalTime;
import java.util.UUID;

public record NewPromotedLocalDto(
        LocalTime startDate,
        LocalTime endDate,
        UUID restaurantId
) {

}
