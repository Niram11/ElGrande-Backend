package com.codecool.gastro.controller;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.service.DishCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dish-categories")
public class DishCategoryController {
    private final DishCategoryService dishCategoryService;

    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<DishCategoryDto>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(dishCategoryService.getAllDishCategories());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DishCategoryDto> createDishCategory(@Valid @RequestBody NewDishCategoryDto newDishCategoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishCategoryService.saveDishCategory(newDishCategoryDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DishCategoryDto> deleteDishCategory(@PathVariable UUID id) {
        dishCategoryService.deleteDishCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
