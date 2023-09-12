package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.entity.DishCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DishCategoryMapper
{
    DishCategoryDto toDto(DishCategory menuCategory);

    DishCategory dtoToDishCategory(NewDishCategoryDto menuCategoryDto);

    DishCategory dtoToDishCategory(UUID id);

    DishCategory dtoToDishCategory(NewDishCategoryDto menuCategoryDto, UUID id);

}
