package com.codecool.gastro.DTO.location;

import java.util.UUID;

public record NewLocationDTO(
        double latitude,
        double longitude,
        UUID restaurantID
) {}
