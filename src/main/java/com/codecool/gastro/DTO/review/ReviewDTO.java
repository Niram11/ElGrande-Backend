package com.codecool.gastro.DTO.review;


import java.util.UUID;

public record ReviewDTO (
        UUID id,
        String review,
        int grade
//        User user,
//        Restaurant restaurant
) {
}
