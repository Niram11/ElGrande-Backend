package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidation implements Validation<NewReviewDto, Review> {
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;

    public ReviewValidation(RestaurantRepository restaurantRepository, CustomerRepository customerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Review validateEntityById(NewReviewDto newReviewDto) {
        return null;
    }

    public void validateSaveReview(NewReviewDto newReviewDto) {
        restaurantRepository.findById(newReviewDto.restaurantId())
                .orElseThrow(() -> new ObjectNotFoundException(newReviewDto.restaurantId(), Restaurant.class));
        customerRepository.findById(newReviewDto.customerId())
                .orElseThrow(() -> new ObjectNotFoundException(newReviewDto.customerId(), Customer.class));
    }
}
