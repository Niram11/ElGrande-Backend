package com.codecool.gastro.dto.review;

import com.codecool.gastro.controller.validation.CustomerExist;
import com.codecool.gastro.controller.validation.RestaurantExist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record NewReviewDto(
        @NotBlank(message = "Comment cannot be empty")
        String comment,
        @Min(value = 1, message = "Grade must be greater then or equal 1")
        @Max(value = 10, message = "Grade must be less then or equal 10")
        Double grade,
        @CustomerExist
        UUID customerId,
        @RestaurantExist
        UUID restaurantId
) {
}