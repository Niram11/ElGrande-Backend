package com.codecool.gastro.mapper;

import com.codecool.gastro.controller.dto.IdNamePairDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMenuMapper {

    public RestaurantMenu mapNewRestaurantMenuDtoToEntity(NewRestaurantMenuDto dto) {
        return new RestaurantMenu(
                dto.dishName(),
                dto.price()
        );
    }

    public RestaurantMenuDto mapRestaurantEntityToDto(RestaurantMenu entity) {
        return new RestaurantMenuDto(
                entity.getId(),
                entity.getDishName(),
                entity.getPrice(),
                mapIngredients(entity)
        );
    }

    private List<IdNamePairDto> mapIngredients(RestaurantMenu entity){
        return entity.getIngredients().stream()
                .map(rm -> new IdNamePairDto(rm.getId(), rm.getName()))
                .toList();
    }
}
