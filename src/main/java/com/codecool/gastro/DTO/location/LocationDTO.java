package com.codecool.gastro.DTO.location;

import java.util.UUID;

public record LocationDTO(
    UUID id,
    double latitude,
    double longitude
//    UUID restaurantUUID
    ) {

}

