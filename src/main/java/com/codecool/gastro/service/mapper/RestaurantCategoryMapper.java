package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantCategoryMapper {

    RestaurantCategoryDto toDto(RestaurantCategory restaurantCategory);

    RestaurantCategory dtoToRestaurantCategory(NewRestaurantCategoryDto newRestaurantCategoryDto);

    RestaurantCategory dtoToRestaurantCategory(NewRestaurantCategoryDto newRestaurantCategoryDto, UUID id);

    RestaurantCategory dtoToRestaurantCategory(UUID id);

}