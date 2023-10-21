package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

public class PromotedLocalValidation implements Validation<NewPromotedLocalDto> {
    private final RestaurantRepository restaurantRepository;

    public PromotedLocalValidation(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateUpdate(NewPromotedLocalDto newPromotedLocalDto) {
        restaurantRepository.findById(newPromotedLocalDto.restaurantId())
                .orElseThrow(() -> new ObjectNotFoundException(newPromotedLocalDto.restaurantId(), Restaurant.class));
    }
}
