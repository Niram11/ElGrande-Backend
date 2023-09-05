package com.codecool.gastro.dto.locations;

import java.util.UUID;

public record LocationsDTO(
    UUID id,
    double latitude,
    double longitude

//    UUID restaurantId
    ) {

}

