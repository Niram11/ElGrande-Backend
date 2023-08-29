package com.codecool.gastro.mapper;

import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.repository.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDTO restaurantToDTO(Restaurant restaurant);

    Restaurant DTOToRestaurant(NewRestaurantDTO newRestaurantDTO);
}
