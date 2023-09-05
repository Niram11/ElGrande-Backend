package com.codecool.gastro.dto.location;

import java.util.UUID;

public record LocationDto(
    UUID id,
    double latitude,
    double longitude

//    UUID restaurantID
    ) {

}

