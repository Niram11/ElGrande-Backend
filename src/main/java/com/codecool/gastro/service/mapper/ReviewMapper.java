package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.review.NewReviewDTO;
import com.codecool.gastro.DTO.review.ReviewDTO;
import com.codecool.gastro.repository.entity.Review;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO reviewToDTO (Review review);

    Review DTOToReview (NewReviewDTO newReviewDTO);

    Review DTOToReview(UUID id);

    Review DTOToReview(NewReviewDTO newReviewDTO, UUID id);
}
