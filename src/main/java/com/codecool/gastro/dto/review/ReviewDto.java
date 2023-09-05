package com.codecool.gastro.dto.review;


import java.util.UUID;

public record ReviewDto(
        UUID id,
        String review,
        java.math.BigDecimal grade
        //TODO:(Niram11) uncomment code after creating user and restaurant classes
//        User user,
//        Restaurant restaurant
) {
}