package com.codecool.gastro.controller.validation;

import com.codecool.gastro.repository.RestaurantRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestaurantExistValidator implements ConstraintValidator<RestaurantExist, UUID> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantExistValidator(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        return restaurantRepository.findById(id).isPresent();
    }
}
