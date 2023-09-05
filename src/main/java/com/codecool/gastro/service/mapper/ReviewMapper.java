package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.entity.Review;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto reviewToDto(Review review);

    Review DtoToReview(NewReviewDto newReviewDto);

    Review DtoToReview(UUID id);

    Review DtoToReview(NewReviewDto newReviewDto, UUID id);
}