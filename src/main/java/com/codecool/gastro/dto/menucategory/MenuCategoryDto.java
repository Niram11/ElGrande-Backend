package com.codecool.gastro.dto.menucategory;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MenuCategoryDto(
        UUID id,

        String category
)
{
}
