package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantCategoryMapperTest {
    private final RestaurantCategoryMapper mapper = Mappers.getMapper(RestaurantCategoryMapper.class);
    private final static UUID CATEGORY_ID = UUID.randomUUID();
    private final static String CATEGORY_NAME = "CategoryName";

    @Test
    void testMappingToDtoShouldMapToDtoWhenProvidingValidData() {
        //Given
        RestaurantCategory restaurantCategory = new RestaurantCategory();
        restaurantCategory.setId(CATEGORY_ID);
        restaurantCategory.setCategory(CATEGORY_NAME);

        //When
        RestaurantCategoryDto restaurantCategoryDto = mapper.toDto(restaurantCategory);

        //Then
        assertEquals(restaurantCategoryDto.id(), CATEGORY_ID);
        assertEquals(restaurantCategoryDto.category(), restaurantCategory.getCategory());
    }

    @Test
    void testDtoToRestaurantCategoryShouldMapDtoToRestaurantCategoryWhenProvidingValidData() {
        //Given
        NewRestaurantCategoryDto newRestaurantCategoryDto = new NewRestaurantCategoryDto(CATEGORY_NAME);

        //When
        RestaurantCategory restaurantCategory = mapper.dtoToRestaurantCategory(newRestaurantCategoryDto);

        //Then
        assertEquals(restaurantCategory.getCategory(), newRestaurantCategoryDto.category());
    }

    @Test
    void testDtoToRestaurantCategoryWithIdShouldMapDtoToRestaurantCategoryWithIdWhenProvidingValidData() {
        //When
        RestaurantCategory restaurantCategory = mapper.dtoToRestaurantCategory(CATEGORY_ID);

        //Then
        assertEquals(restaurantCategory.getId(), CATEGORY_ID);
    }
}

