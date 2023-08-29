package com.codecool.gastro.mapper;

import com.codecool.gastro.controller.dto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.RestaurantMenuDto;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuMapper {

    public RestaurantMenu mapNewRestaurantMenuDtoToEntity(NewRestaurantMenuDto dto) {
        return new RestaurantMenu(
                dto.dishName(),
                dto.ingredients(),
                dto.price()
        );
    }

    public RestaurantMenuDto mapRestaurantEntityToDto(RestaurantMenu entity) {
        return new RestaurantMenuDto(
                entity.getId(),
                entity.getDishName(),
                entity.getIngredients(),
                entity.getPrice()
        );
    }
}
