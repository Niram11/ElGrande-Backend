package com.codecool.gastro.dto.form;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;

public record NewFormDto(
        NewRestaurantDto newRestaurantDto,
        NewLocationDto newLocationDto,
        NewBusinessHourDto newBusinessHourDto,
        NewAddressDto newAddressDto
) {
}
