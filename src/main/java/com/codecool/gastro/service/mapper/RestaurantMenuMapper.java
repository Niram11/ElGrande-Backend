package com.codecool.gastro.service.mapper;

import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMenuMapper {

    RestaurantMenuDto getMenuDto(RestaurantMenu menu);

    RestaurantMenu dtoToRestaurantMenu(NewRestaurantMenuDto menuDto);

    @Mapping(source = "id", target = "id")
    RestaurantMenu dtoToRestaurantMenu(UUID id);
}
