package com.codecool.gastro.service;


import com.codecool.gastro.DTO.review.NewReviewDTO;
import com.codecool.gastro.DTO.review.ReviewDTO;
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

    public List<ReviewDTO> getReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::reviewToDTO).toList();
    }

    public ReviewDTO getReviewByUUID(UUID id) {
        return reviewRepository.findById(id).map(reviewMapper::reviewToDTO)
                .orElseThrow(() -> new RuntimeException());
    }

    public ReviewDTO saveReview(NewReviewDTO newReviewDTO) {
        Review savedReview = reviewRepository.save(reviewMapper.DTOToReview(newReviewDTO));
        return reviewMapper.reviewToDTO(savedReview);
    }

    public ReviewDTO updateReview(UUID id, NewReviewDTO newReviewDTO) {
        Review updatedReview = reviewRepository.save(reviewMapper.DTOToReview(newReviewDTO, id));
        return reviewMapper.reviewToDTO(updatedReview);

    }

    public void deleteReview(UUID id) {
        Review deletedReview = reviewMapper.DTOToReview(id);
        reviewRepository.delete(deletedReview);
    }


}
