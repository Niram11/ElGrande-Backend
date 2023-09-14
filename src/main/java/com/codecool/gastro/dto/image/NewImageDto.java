package com.codecool.gastro.dto.image;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewImageDto(

        @NotBlank(message = "PathToImage cannot be empty")
        String pathToImage,

        UUID restaurantId
) {
}
