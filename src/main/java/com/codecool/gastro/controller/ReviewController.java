package com.codecool.gastro.controller;


import com.codecool.gastro.dto.review.DetailedReviewDto;
import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(params = "customerId")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReviewDto>> getReviewsByCustomerId(@RequestParam("customerId") UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewByCustomerId(customerId));
    }

    @GetMapping(value = "/details", params = "restaurantId")
    public ResponseEntity<List<DetailedReviewDto>> getDetailedReviewsByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getDetailedReviewsByRestaurantId(restaurantId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DetailedReviewDto> createNewReview(@Valid @RequestBody NewReviewDto newReviewDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.saveReview(newReviewDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}