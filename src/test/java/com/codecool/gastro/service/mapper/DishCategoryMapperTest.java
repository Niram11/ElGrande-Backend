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
    DishCategoryMapper mapper = Mappers.getMapper(DishCategoryMapper.class);

    private UUID dishCategoryId;
    private DishCategory dishCategory;

    private NewDishCategoryDto newDishCategoryDto;

    @BeforeEach
    void setUp() {
        dishCategoryId = UUID.randomUUID();

        dishCategory = new DishCategory();
        dishCategory.setId(UUID.randomUUID());
        dishCategory.setCategory("Category");

        newDishCategoryDto = new NewDishCategoryDto(
                "NewCategory"
        );

    }

    @Test
    void testToDto_ShouldReturnDishCategoryDto_WhenCalled() {
        // when
        DishCategoryDto dishCategoryDto = mapper.toDto(dishCategory);

        // then
        assertEquals(dishCategoryDto.id(), dishCategory.getId());
        assertEquals(dishCategoryDto.category(), dishCategory.getCategory());
    }

    @Test
    void testDtoToDishCategory_ShouldReturnDishCategory_WhenProvidingId() {
        // when
        DishCategory testedDishCategory = mapper.dtoToDishCategory(dishCategoryId);

        // then
        assertEquals(testedDishCategory.getId(), dishCategoryId);
    }

    @Test
    void testDtoToDishCategory_ShouldReturnDishCategory_WhenProvidingDto() {
        // when
        DishCategory testedDishCategory = mapper.dtoToDishCategory(newDishCategoryDto);

        // then
        assertEquals(testedDishCategory.getCategory(), newDishCategoryDto.category());
    }
}