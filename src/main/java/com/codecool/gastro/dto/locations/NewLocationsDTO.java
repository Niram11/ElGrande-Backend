package com.codecool.gastro.dto.locations;

import java.util.UUID;

public record NewLocationsDTO(
        double latitude,
        double longitude,
        UUID restaurantID
) {}
