package com.codecool.gastro.dto.image;

import java.util.UUID;

public record ImageDto(
        UUID id,
        String pathToImage
) {
}
