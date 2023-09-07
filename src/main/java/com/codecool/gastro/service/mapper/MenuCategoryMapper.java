package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.menucategory.MenuCategoryDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.repository.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper
{
    MenuCategoryDto menuCategoryToDto(MenuCategory menuCategory);

    MenuCategory dtoToMenuCategory(NewMenuCategoryDto menuCategoryDto);

    @Mapping(source = "id", target = "id")
    MenuCategory dtoToMenuCategory(UUID id);

    @Mapping(source = "id", target = "id")
    MenuCategory dtoToMenuCategory(NewMenuCategoryDto menuCategoryDto, UUID id);

}
