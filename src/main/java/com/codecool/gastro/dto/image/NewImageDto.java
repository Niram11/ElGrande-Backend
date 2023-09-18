package com.codecool.gastro.dto.image;

import com.codecool.gastro.controller.validation.RestaurantExist;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewImageDto(

        @NotBlank(message = "Path to image cannot be empty")
        String pathToImage,
        @RestaurantExist
        UUID restaurantId
) {
}
