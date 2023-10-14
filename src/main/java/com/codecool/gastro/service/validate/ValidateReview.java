package com.codecool.gastro.service.validate;

import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

public class ValidateReview implements Validate {
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;

    public ValidateReview(RestaurantRepository restaurantRepository, CustomerRepository customerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void validateUpdate(NewReviewDto newReviewDto) {
        restaurantRepository.findById(newReviewDto.restaurantId())
                .orElseThrow(() -> new ObjectNotFoundException(newReviewDto.restaurantId(), Restaurant.class));
        customerRepository.findById(newReviewDto.customerId())
                .orElseThrow(() -> new ObjectNotFoundException(newReviewDto.customerId(), Restaurant.class));
    }
}
