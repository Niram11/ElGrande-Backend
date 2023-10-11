package com.codecool.gastro.dto.image;

import com.codecool.gastro.dto.NewEntityDto;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewImageDto(
        @NotBlank(message = "Path to image cannot be empty")
        String pathToImage,
        UUID restaurantId
) implements NewEntityDto {
}
