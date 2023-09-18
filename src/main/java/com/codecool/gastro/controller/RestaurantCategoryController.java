package com.codecool.gastro.controller;


import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/restaurant-categories")
public class RestaurantCategoryController {

    private final RestaurantCategoryService restaurantCategoryService;

    public RestaurantCategoryController(RestaurantCategoryService restaurantCategoryService1) {
        this.restaurantCategoryService = restaurantCategoryService1;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantCategoryDto>> getAllRestaurantCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantCategoryService.getRestaurantCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantCategoryDto> getRestaurantCategory(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(restaurantCategoryService.getRestaurantCategory(id));
    }

    @PostMapping
    public ResponseEntity<RestaurantCategoryDto> createNewRestaurantCategory(@Valid @RequestBody NewRestaurantCategoryDto newRestaurantCategoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantCategoryService.saveRestaurantCategory(newRestaurantCategoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantCategoryDto> updateRestaurantCategory(@PathVariable UUID id,
                                                          @Valid @RequestBody NewRestaurantCategoryDto newRestaurantCategoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantCategoryService.updateRestaurantCategory(id, newRestaurantCategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestaurantCategoryDto> deleteRestaurantCategory(@PathVariable UUID id) {
        restaurantCategoryService.deleteRestaurantCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}