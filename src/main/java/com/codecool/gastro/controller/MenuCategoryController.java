package com.codecool.gastro.controller;


import com.codecool.gastro.dto.menucategory.MenuCategoryDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-categories")
public class MenuCategoryController
{
    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService)
    {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping
    public List<MenuCategoryDto> getCategories()
    {
        return menuCategoryService.getAllMenuCategories();
    }

    @GetMapping("/{id}")
    public MenuCategoryDto getCategoryBy(@PathVariable UUID id)
    {
        return menuCategoryService.getMenuCategoryById(id);
    }

    @PostMapping
    public MenuCategoryDto createMenuCategory(@Valid @RequestBody NewMenuCategoryDto newMenuCategoryDto)
    {
        return menuCategoryService.saveMenuCategory(newMenuCategoryDto);
    }


    @PutMapping("/{id}")
    public MenuCategoryDto updateMenuCategory(@PathVariable UUID id,
                                              @Valid @RequestBody NewMenuCategoryDto newMenuCategoryDto)
    {
        return menuCategoryService.updateMenuCategory(id, newMenuCategoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuCategory(@PathVariable UUID id)
    {
        menuCategoryService.deleteMenuCategory(id);
    }
}
