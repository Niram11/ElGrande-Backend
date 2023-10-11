package com.codecool.gastro.service;


import com.codecool.gastro.dto.review.DetailedReview;
import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public ReviewDto getReviewById(UUID id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Review.class));
    }

    public List<ReviewDto> getReviewsByRestaurant(UUID id, Pageable pageable) {
        return reviewRepository.getReviewsByRestaurant(id, pageable)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public List<ReviewDto> getReviewByCustomerId(UUID id) {
        return reviewRepository.getReviewsByCustomerId(id)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public ReviewDto saveReview(NewReviewDto newReviewDTO) {
        //TODO: is restaurant and customer exists and put to class validation
        Review savedReview = reviewMapper.dtoToReview(newReviewDTO);
        savedReview.setSubmissionTime(LocalDate.now());
        return reviewMapper.toDto(reviewRepository.save(savedReview));
    }


    public void deleteReview(UUID id) {
        reviewRepository.delete(reviewMapper.dtoToReview(id));
    }

    public List<DetailedReview> getDetailedReviewsByRestaurantId(UUID restaurantId) {
        return reviewRepository.findDetailedReviewsByRestaurantId(restaurantId)
                .stream()
                .map(reviewMapper::toDetailedDto)
                .toList();
    }
}