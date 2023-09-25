package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.entity.DishCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DishCategoryMapperTest {

    private DishCategoryMapper mapper = Mappers.getMapper(DishCategoryMapper.class);

    @Test
    void testToDto_ShouldReturnDishCategoryDto_WhenCalled() {
        // given
        DishCategory dishCategory = new DishCategory();
        dishCategory.setId(UUID.randomUUID());
        dishCategory.setCategory("Category");

        // when
        DishCategoryDto dishCategoryDto = mapper.toDto(dishCategory);

        // then
        assertEquals(dishCategoryDto.id(), dishCategory.getId());
        assertEquals(dishCategoryDto.category(), dishCategory.getCategory());
    }

    @Test
    void testDtoToDishCategory_ShouldReturnDishCategory_WhenCalled() {
        // given
        UUID dishCategoryId = UUID.randomUUID();
        NewDishCategoryDto newDishCategoryDto = new NewDishCategoryDto("Category");

        // when
        DishCategory dishCategoryOne = mapper.dtoToDishCategory(dishCategoryId);
        DishCategory dishCategoryTwo = mapper.dtoToDishCategory(dishCategoryId, newDishCategoryDto);
        DishCategory dishCategoryThree = mapper.dtoToDishCategory(newDishCategoryDto);

        // then
        assertEquals(dishCategoryOne.getId(), dishCategoryId);
        assertEquals(dishCategoryTwo.getId(), dishCategoryId);
        assertEquals(dishCategoryTwo.getCategory(), newDishCategoryDto.category());
        assertEquals(dishCategoryThree.getCategory(), newDishCategoryDto.category());
    }
}