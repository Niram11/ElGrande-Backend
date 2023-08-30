package com.codecool.gastro.controller.dto;

import java.util.UUID;

public record IdNamePairDto(
        UUID id,

        String name
) {
}
