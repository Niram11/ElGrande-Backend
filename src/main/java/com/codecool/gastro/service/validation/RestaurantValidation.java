package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.Set;
import java.util.UUID;

public class RestaurantValidation implements Validation<UUID>{
    private final RestaurantRepository restaurantRepository;

    public RestaurantValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateUpdate(UUID id) {
        restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }


    public void validateRestaurantsSet(Set<RestaurantDto> restaurants) {
        restaurants.forEach(restaurantDto -> restaurantRepository.findById(restaurantDto.id())
                .orElseThrow(() -> new ObjectNotFoundException(restaurantDto.id(), Restaurant.class)));
    }


}
