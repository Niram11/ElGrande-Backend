package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.restaurantrestaurantcategory.RestaurantRestaurantCategoryDTO;
import com.codecool.gastro.repository.entity.RestaurantRestaurantCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantRestaurantCategoryMapper {

    RestaurantRestaurantCategoryDTO restaurantRestaurantCategoryToDTO(RestaurantRestaurantCategory
                                                                              restaurantRestaurantCategory);

    RestaurantRestaurantCategory DTOToRestaurantRestaurantCategory(RestaurantRestaurantCategoryDTO
                                                                           restaurantRestaurantCategoryDTO);

    RestaurantRestaurantCategory DTOToRestaurantRestaurantCategory(UUID id);

    RestaurantRestaurantCategory DTOToRestaurantRestaurantCategory(RestaurantRestaurantCategoryDTO
                                                                           restaurantRestaurantCategoryDTO, UUID id);
}
