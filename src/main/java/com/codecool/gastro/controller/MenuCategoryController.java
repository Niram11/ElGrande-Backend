package com.codecool.gastro.controller;


import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.service.DishCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-categories")
public class MenuCategoryController {
    private final DishCategoryService menuCategoryService;

    public MenuCategoryController(DishCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping
    public List<DishCategoryDto> getCategories() {
        return menuCategoryService.getAllDishCategories();
    }

    @GetMapping("/{id}")
    public DishCategoryDto getCategory(@PathVariable UUID id) {
        return menuCategoryService.getDishCategoryById(id);
    }

    @PostMapping
    public DishCategoryDto createMenuCategory(@Valid @RequestBody NewDishCategoryDto newDishCategoryDto) {
        return menuCategoryService.saveDishCategory(newDishCategoryDto);
    }


    @PutMapping("/{id}")
    public DishCategoryDto updateMenuCategory(@PathVariable UUID id,
                                              @Valid @RequestBody NewDishCategoryDto newDishCategoryDto) {
        return menuCategoryService.updateDishCategory(id, newDishCategoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuCategory(@PathVariable UUID id) {
        menuCategoryService.deleteDishCategory(id);
    }
}
