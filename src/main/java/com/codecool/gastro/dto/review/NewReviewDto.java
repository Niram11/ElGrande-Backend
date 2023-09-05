package com.codecool.gastro.dto.review;

public record NewReviewDto(
        String review,
        int grade
        //TODO:(Niram11) uncomment code after creating user and restaurant classes
//        User user,
//        Restaurant restaurant
) {
}