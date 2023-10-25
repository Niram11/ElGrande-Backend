package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.Set;

public class RestaurantValidation implements Validation<NewRestaurantDto>{
    private final RestaurantRepository restaurantRepository;

    public RestaurantValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateUpdate(NewRestaurantDto newRestaurantDto) {
    }

    public void validateRestaurantsSet(Set<RestaurantDto> restaurants) {
        restaurants.forEach(restaurantDto -> restaurantRepository.findById(restaurantDto.id())
                .orElseThrow(() -> new ObjectNotFoundException(restaurantDto.id(), Restaurant.class)));
    }


}
