package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PromotedLocalValidation implements Validation<UUID, Restaurant> {
    private final RestaurantRepository restaurantRepository;

    public PromotedLocalValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant validateEntityById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }
}
