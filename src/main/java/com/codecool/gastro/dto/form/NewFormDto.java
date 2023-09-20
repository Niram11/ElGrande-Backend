package com.codecool.gastro.dto.form;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;

import java.util.List;

public record NewFormDto(
        NewRestaurantDto restaurant,
        NewLocationDto location,
        List<NewBusinessHourDto> businessHour,
        NewAddressDto address
) {
}
