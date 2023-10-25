package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.UUID;

public class PromotedLocalValidation implements Validation<UUID> {
    private final RestaurantRepository restaurantRepository;

    public PromotedLocalValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateUpdate(UUID id) {
        restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, PromotedLocal.class));
    }
}
