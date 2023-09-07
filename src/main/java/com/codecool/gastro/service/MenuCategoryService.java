package com.codecool.gastro.service;

import com.codecool.gastro.dto.menucategory.MenuCategoryDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.repository.MenuCategoryRepository;
import com.codecool.gastro.repository.entity.MenuCategory;
import com.codecool.gastro.service.mapper.MenuCategoryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return menuCategoryMapper.menuCategoryToDto(savedMenuCategory);
    }

    public List<MenuCategoryDto> getAllMenuCategories()
    {
        return menuCategoryRepository.findAll()
                .stream().map(menuCategoryMapper::menuCategoryToDto).toList();
    }

    public MenuCategoryDto getMenuCategoryById(UUID id)
    {
        return menuCategoryRepository.findById(id)
                .map(menuCategoryMapper::menuCategoryToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public MenuCategoryDto updateMenuCategory(UUID id, NewMenuCategoryDto newMenuCategoryDto)
    {
        MenuCategory updatedMenuCategory = menuCategoryRepository.save(menuCategoryMapper.
                dtoToMenuCategory(newMenuCategoryDto, id));
        return menuCategoryMapper.menuCategoryToDto(updatedMenuCategory);
    }

    public void deleteMenuCategory(UUID id)
    {
        menuCategoryRepository.delete(menuCategoryMapper.dtoToMenuCategory(id));
    }
}
