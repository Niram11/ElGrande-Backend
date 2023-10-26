package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PromotedLocalValidation implements Validation<UUID> {
    private final RestaurantRepository restaurantRepository;

    public PromotedLocalValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateEntityById(UUID id) {
        restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, PromotedLocal.class));
    }
}
