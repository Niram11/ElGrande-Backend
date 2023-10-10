package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.repository.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DishMapper {


    DishDto toDto(Dish dish);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    Dish dtoToDish(NewDishDto newDishDto);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updatedDishFromDto(EditDishDto editDishDto, @MappingTarget Dish dish);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "dishName", ignore = true)
    @Mapping(target = "id", source = "id")
    Dish dtoToDish(UUID id);
}
