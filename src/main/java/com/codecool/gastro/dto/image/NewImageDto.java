package com.codecool.gastro.dto.image;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewImageDto(

        @NotBlank
        String pathToImage,

        UUID restaurantId
) {
}
