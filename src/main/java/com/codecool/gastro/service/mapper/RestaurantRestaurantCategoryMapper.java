package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantrestaurantcategory.RestaurantRestaurantCategoryDto;
import com.codecool.gastro.repository.entity.RestaurantRestaurantCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantRestaurantCategoryMapper {

    RestaurantRestaurantCategoryDto restaurantRestaurantCategoryToDto(RestaurantRestaurantCategory
                                                                              restaurantRestaurantCategory);

    RestaurantRestaurantCategory DtoToRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDto
                                                                           newRestaurantRestaurantCategoryDto);

    RestaurantRestaurantCategory DtoToRestaurantRestaurantCategory(UUID id);

    RestaurantRestaurantCategory DtoToRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDto
                                                                           newRestaurantRestaurantCategoryDto, UUID id);
}
