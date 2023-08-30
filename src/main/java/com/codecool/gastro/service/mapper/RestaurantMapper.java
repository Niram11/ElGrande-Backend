package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto restaurantToDto(Restaurant restaurant);

    Restaurant dtoToRestaurant(NewRestaurantDto newRestaurantDTO);

    @Mapping(source = "id", target = "id")
    Restaurant dtoToRestaurant(NewRestaurantDto newRestaurantDTO, UUID id);
}
