package com.codecool.gastro.dto.image;

import jakarta.validation.constraints.NotBlank;

public record NewImageDto(

        @NotBlank
        String pathToImage
) {
}
