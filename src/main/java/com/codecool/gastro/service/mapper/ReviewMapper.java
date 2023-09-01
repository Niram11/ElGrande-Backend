package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.review.NewReviewDTO;
import com.codecool.gastro.DTO.review.ReviewDTO;
import com.codecool.gastro.repository.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO reviewToDTO (Review review);

    Review DTOToReview (NewReviewDTO newReviewDTO);
}
