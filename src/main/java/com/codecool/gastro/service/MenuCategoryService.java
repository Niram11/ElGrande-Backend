package com.codecool.gastro.service;

import com.codecool.gastro.dto.menucategory.MenuCategoryDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.repository.MenuCategoryRepository;
import com.codecool.gastro.repository.entity.MenuCategory;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.MenuCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuCategoryService
{
    private final MenuCategoryMapper menuCategoryMapper;
    private final MenuCategoryRepository menuCategoryRepository;


    public MenuCategoryService(MenuCategoryMapper menuCategoryMapper,
                               MenuCategoryRepository menuCategoryRepository)
    {
        this.menuCategoryMapper = menuCategoryMapper;
        this.menuCategoryRepository = menuCategoryRepository;
    }

    public MenuCategoryDto saveMenuCategory(NewMenuCategoryDto newMenuCategoryDto)
    {
        MenuCategory savedMenuCategory = menuCategoryRepository.save(menuCategoryMapper.
                dtoToMenuCategory(newMenuCategoryDto));
        return menuCategoryMapper.toDto(savedMenuCategory);
    }

    public List<MenuCategoryDto> getAllMenuCategories()
    {
        return menuCategoryRepository.findAll()
                .stream().map(menuCategoryMapper::toDto).toList();
    }

    public MenuCategoryDto getMenuCategoryById(UUID id)
    {
        return menuCategoryRepository.findBy(id)
                .map(menuCategoryMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, MenuCategory.class));
    }

    public MenuCategoryDto updateMenuCategory(UUID id, NewMenuCategoryDto newMenuCategoryDto)
    {
        MenuCategory updatedMenuCategory = menuCategoryRepository.save(menuCategoryMapper.
                dtoToMenuCategory(newMenuCategoryDto, id));
        return menuCategoryMapper.toDto(updatedMenuCategory);
    }

    public void deleteMenuCategory(UUID id)
    {
        menuCategoryRepository.delete(menuCategoryMapper.dtoToMenuCategory(id));
    }
}
