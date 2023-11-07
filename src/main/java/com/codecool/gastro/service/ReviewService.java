package com.codecool.gastro.service;


import com.codecool.gastro.dto.review.DetailedReviewDto;
import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ReviewMapper;
import com.codecool.gastro.service.validation.CustomerValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import com.codecool.gastro.service.validation.ReviewValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewValidation reviewValidation;
    private final RestaurantValidation restaurantValidation;
    private final CustomerValidation customerValidation;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper,
                         ReviewValidation reviewValidation, RestaurantValidation restaurantValidation, CustomerValidation customerValidation) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.reviewValidation = reviewValidation;
        this.restaurantValidation = restaurantValidation;
        this.customerValidation = customerValidation;
    }

    public List<ReviewDto> getReviewByCustomerId(UUID customerId) {
        return reviewRepository.getReviewsByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public DetailedReviewDto saveReview(NewReviewDto newReviewDto) {
        restaurantValidation.validateEntityById(newReviewDto.restaurantId());
        customerValidation.validateEntityById(newReviewDto.customerId());

        Review savedReview = reviewMapper.dtoToReview(newReviewDto);
        savedReview.setSubmissionTime(LocalDate.now());
        reviewRepository.save(savedReview);
        return reviewMapper.toDetailedDto(reviewRepository.findDetailedReviewById(savedReview.getId())
                .orElseThrow(() -> new ObjectNotFoundException(savedReview.getId(), Review.class)));
    }

    public void deleteReview(UUID id) {
        reviewRepository.delete(reviewMapper.dtoToReview(id));
    }

    public List<DetailedReviewDto> getDetailedReviewsByRestaurantId(UUID restaurantId) {
        return reviewRepository.findDetailedReviewsByRestaurantId(restaurantId)
                .stream()
                .map(reviewMapper::toDetailedDto)
                .toList();
    }
}