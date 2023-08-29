package com.codecool.gastro.DTO.locations;

import java.util.UUID;

public record LocationsDTO(
    UUID id,
    double latitude,
    double longitude
//    UUID restaurantUUID
    ) {

}

