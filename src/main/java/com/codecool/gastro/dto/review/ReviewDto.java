package com.codecool.gastro.dto.review;


import java.util.UUID;

public record ReviewDto(
        UUID id,
        String review,
        java.math.BigDecimal grade
//        User user,
//        Restaurant restaurant
) {
}
