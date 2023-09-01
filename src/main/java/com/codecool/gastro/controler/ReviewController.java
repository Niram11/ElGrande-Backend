package com.codecool.gastro.controler;


import com.codecool.gastro.DTO.review.NewReviewDTO;
import com.codecool.gastro.DTO.review.ReviewDTO;
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
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable UUID id) {
        return reviewService.getReviewByUUID(id);
    }

    @PostMapping
    public ReviewDTO createNewReview(@Valid @RequestBody NewReviewDTO newReviewDTO) {
        return reviewService.saveReview(newReviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable UUID id, @Valid @RequestBody NewReviewDTO newReviewDTO) {
        return reviewService.updateReview(id, newReviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }


}
