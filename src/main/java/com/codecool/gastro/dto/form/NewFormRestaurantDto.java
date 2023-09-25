package com.codecool.gastro.dto.form;

import com.codecool.gastro.dto.address.NewFormAddressDto;
import com.codecool.gastro.dto.businesshour.NewFormBusinessHourDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;

import java.util.List;

public record NewFormRestaurantDto(
        NewRestaurantDto restaurant,
        NewLocationDto location,
        List<NewFormBusinessHourDto> businessHour,
        NewFormAddressDto address
) {
}
