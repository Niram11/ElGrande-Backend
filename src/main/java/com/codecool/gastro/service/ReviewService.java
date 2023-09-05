package com.codecool.gastro.service;


import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

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

    public List<ReviewDto> getReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::reviewToDto).toList();
    }

    public ReviewDto getReviewByUUID(UUID id) {
        return reviewRepository.findById(id).map(reviewMapper::reviewToDto)
                .orElseThrow(() -> new RuntimeException());
    }

    public ReviewDto saveReview(NewReviewDto newReviewDTO) {
        Review savedReview = reviewRepository.save(reviewMapper.DtoToReview(newReviewDTO));
        return reviewMapper.reviewToDto(savedReview);
    }

    public ReviewDto updateReview(UUID id, NewReviewDto newReviewDTO) {
        Review updatedReview = reviewRepository.save(reviewMapper.DtoToReview(newReviewDTO, id));
        return reviewMapper.reviewToDto(updatedReview);

    }

    public void deleteReview(UUID id) {
        Review deletedReview = reviewMapper.DtoToReview(id);
        reviewRepository.delete(deletedReview);
    }
}