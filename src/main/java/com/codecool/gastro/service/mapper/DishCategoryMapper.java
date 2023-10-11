package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.entity.DishCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DishCategoryMapper {
    DishCategoryDto toDto(DishCategory dishCategory);

    @Mapping(target = "id", ignore = true)
    DishCategory dtoToDishCategory(NewDishCategoryDto dishCategoryDto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", source = "id")
    DishCategory dtoToDishCategory(UUID id);

}
