package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class RestaurantValidation implements Validation<Restaurant> {
    private final RestaurantRepository restaurantRepository;

    public RestaurantValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant validateEntityById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }

}
