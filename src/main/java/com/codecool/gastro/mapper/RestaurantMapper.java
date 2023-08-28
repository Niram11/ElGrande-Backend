package com.codecool.gastro.mapper;

import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.repository.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(source = "id", target = "id")
    RestaurantDTO restaurantToDTO(Restaurant restaurant);

    @Mapping(source = "name", target = "name")
    Restaurant DTOToRestaurant(NewRestaurantDTO newRestaurantDTO);
}
