package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReviewValidation implements Validation<Review> {
    private final ReviewRepository reviewRepository;

    public ReviewValidation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review validateEntityById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Review.class));
    }
}
