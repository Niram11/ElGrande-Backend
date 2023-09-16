package com.codecool.gastro.dto.restaurant;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;
import java.util.UUID;

public record DetailedRestaurantDto(
        UUID id,
        String name,
        String description,
        String website,
        Integer contactNumber,
        String contactEmail,
        String[] imagesPaths,
        BigDecimal averageGrade
) {
}
