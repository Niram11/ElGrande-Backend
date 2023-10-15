package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFormHandler implements FormHandler<Restaurant> {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public RestaurantFormHandler(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant) {
        restaurantMapper.updateRestaurantFromDto(formDto.restaurant(), restaurant);
        restaurantRepository.save(restaurant);
    }

}
