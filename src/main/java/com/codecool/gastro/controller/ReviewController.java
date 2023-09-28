package com.codecool.gastro.controller;


import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewBy(id));
    }
//TODO: specification ASC DESC, pagable
    @GetMapping(params = "restaurantId")
    public ResponseEntity<List<ReviewDto>> getReviewsForRestaurant(@RequestParam("restaurantId") UUID restaurantId,
                                                                   Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewsByRestaurant(restaurantId, pageable));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createNewReview(@Valid @RequestBody NewReviewDto newReviewDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.saveReview(newReviewDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable UUID id, @Valid @RequestBody NewReviewDto newReviewDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.updateReview(id, newReviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}