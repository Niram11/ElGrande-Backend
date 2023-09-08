package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMenuMapper {

    RestaurantMenuDto toDto(RestaurantMenu menu);

    RestaurantMenu dtoToRestaurantMenu(NewRestaurantMenuDto newRestaurantMenuDto);

    RestaurantMenu dtoToRestaurantMenu(NewRestaurantMenuDto newRestaurantMenuDto, UUID id);

    RestaurantMenu dtoToRestaurantMenu(UUID id);

}
