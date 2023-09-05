package com.codecool.gastro.dto.location;

import java.util.UUID;

public record NewLocationDto(
        double latitude,
        double longitude,
        UUID restaurantID
) {}
