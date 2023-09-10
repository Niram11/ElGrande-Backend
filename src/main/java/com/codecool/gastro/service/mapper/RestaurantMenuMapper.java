package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMenuMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    RestaurantMenuDto toDto(RestaurantMenu menu);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    RestaurantMenu dtoToRestaurantMenu(NewRestaurantMenuDto newRestaurantMenuDto);

    @Mapping(source = "newRestaurantMenuDto.restaurantId", target = "restaurant.id")
    RestaurantMenu dtoToRestaurantMenu(NewRestaurantMenuDto newRestaurantMenuDto, UUID id);

    RestaurantMenu dtoToRestaurantMenu(UUID id);

}
