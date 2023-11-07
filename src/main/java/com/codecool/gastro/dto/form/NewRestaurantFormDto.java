package com.codecool.gastro.dto.form;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

public record NewRestaurantFormDto(
        @Valid
        NewRestaurantDto restaurant,
        @Valid
        NewLocationDto location,
        @Valid
        List<NewBusinessHourDto> businessHour,
        @Valid
        NewAddressDto address,
        UUID customerId
) {
}
