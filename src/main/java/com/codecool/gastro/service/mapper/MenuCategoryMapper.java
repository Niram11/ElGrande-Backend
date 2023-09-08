package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.menucategory.MenuCategoryDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.repository.entity.MenuCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper
{
    MenuCategoryDto toDto(MenuCategory menuCategory);

    MenuCategory dtoToMenuCategory(NewMenuCategoryDto menuCategoryDto);

    MenuCategory dtoToMenuCategory(UUID id);

    MenuCategory dtoToMenuCategory(NewMenuCategoryDto menuCategoryDto, UUID id);

}
