package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.repository.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    DishDto toDto(Dish menu);

    @Mapping(source = "restaurantId", target = "restaurant.id")
    Dish dtoToDish(NewDishDto newDishDto);

    @Mapping(source = "newDishDto.restaurantId", target = "restaurant.id")
    Dish dtoToDish(NewDishDto newDishDto, UUID id);

    Dish dtoToDish(UUID id);

}
