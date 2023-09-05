package com.codecool.gastro.controler;


import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public ReviewDto getReview(@PathVariable UUID id) {
        return reviewService.getReviewByUUID(id);
    }

    @PostMapping
    public ReviewDto createNewReview(@Valid @RequestBody NewReviewDto newReviewDTO) {
        return reviewService.saveReview(newReviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable UUID id, @Valid @RequestBody NewReviewDto newReviewDTO) {
        return reviewService.updateReview(id, newReviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }


}
