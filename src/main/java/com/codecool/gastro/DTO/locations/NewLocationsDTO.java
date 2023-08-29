package com.codecool.gastro.DTO.locations;

import java.util.UUID;

public record NewLocationsDTO(
        double latitude,
        double longitude,
        UUID restaurantID
) {}
