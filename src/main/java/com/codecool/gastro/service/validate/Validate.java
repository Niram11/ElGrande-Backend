package com.codecool.gastro.service.validate;


import com.codecool.gastro.dto.review.NewReviewDto;

public interface Validate <T>{

    void validateUpdate(T dto);
}