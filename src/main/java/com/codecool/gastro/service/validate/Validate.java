package com.codecool.gastro.service.validate;


import com.codecool.gastro.dto.review.NewReviewDto;

public interface Validate {
    //TODO: improve dto extension)

    void validateUpdate(NewReviewDto newReviewDto);
}