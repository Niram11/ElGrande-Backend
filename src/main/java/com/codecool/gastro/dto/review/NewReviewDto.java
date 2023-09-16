package com.codecool.gastro.dto.review;

import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewReviewDto(
        @NotBlank(message = "Review cannot be empty")
        String comment,
        @Min(value = 1, message = "Grade must be greater then or equal 1")
        @Max(value = 10, message = "Grade must be less then or equal 10")
        Integer grade,
        UUID customerId,
        UUID restaurantId
) {
}