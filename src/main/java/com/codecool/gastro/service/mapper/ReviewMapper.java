package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.review.DetailedReview;
import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.repository.projection.DetailedReviewProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toDto(Review review);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "restaurantId", target = "restaurant.id")
    Review dtoToReview(NewReviewDto newReviewDto);

    Review dtoToReview(UUID id);

    DetailedReview toDetailedDto(DetailedReviewProjection detailedReviewProjection);
}