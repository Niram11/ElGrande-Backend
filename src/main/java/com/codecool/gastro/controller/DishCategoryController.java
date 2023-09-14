package com.codecool.gastro.controller;


import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.service.DishCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/dish-categories")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;

    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @GetMapping
    public List<DishCategoryDto> getCategories() {
        return dishCategoryService.getAllDishCategories();
    }

    @GetMapping("/{id}")
    public DishCategoryDto getCategory(@PathVariable UUID id) {
        return dishCategoryService.getDishCategoryById(id);
    }

    @PostMapping
    public DishCategoryDto createDishCategory(@Valid @RequestBody NewDishCategoryDto newDishCategoryDto) {
        return dishCategoryService.saveDishCategory(newDishCategoryDto);
    }


    @PutMapping("/{id}")
    public DishCategoryDto updateDishCategory(@PathVariable UUID id,
                                              @Valid @RequestBody NewDishCategoryDto newDishCategoryDto) {
        return dishCategoryService.updateDishCategory(id, newDishCategoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDishCategory(@PathVariable UUID id) {
        dishCategoryService.deleteDishCategory(id);
    }
}
