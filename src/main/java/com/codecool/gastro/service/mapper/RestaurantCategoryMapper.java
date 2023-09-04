package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.restaurantcategory.RestaurantCategoryDTO;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantCategoryMapper {

    RestaurantCategoryDTO restaurantCategoryToDTO(RestaurantCategory restaurantCategory);

    RestaurantCategory DTOToRestaurantCategory(RestaurantCategoryDTO restaurantCategoryDTO);

    RestaurantCategory DTOToRestaurantCategory(UUID id);

    RestaurantCategory DTOToRestaurantCategory(RestaurantCategoryDTO restaurantCategoryDTO, UUID id);
}
