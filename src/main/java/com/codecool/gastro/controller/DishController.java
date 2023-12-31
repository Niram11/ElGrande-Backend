package com.codecool.gastro.controller;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishById(id));
    }

    @GetMapping(params = {"restaurantId"})
    public ResponseEntity<List<DishDto>> getDishesByRestaurant(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishesByRestaurantId(restaurantId));
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<DishDto> createNewDish(@Valid @RequestBody NewDishDto newDishDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.saveNewDish(newDishDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<DishDto> updateDish(@PathVariable UUID id, @Valid @RequestBody EditDishDto editDishDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.updateDish(id, editDishDto));
    }

    @PutMapping("/{id}/ingredients")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<DishDto> assignIngredientToDish(@PathVariable UUID id, @Valid @RequestBody Set<NewIngredientDto> ingredients) {
        dishService.assignIngredientToDish(id, ingredients);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/dish-categories")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<DishDto> assignDishCategoryToDish(@PathVariable UUID id, @Valid @RequestBody Set<NewDishCategoryDto> categories) {
        dishService.assignDishCategoryToDish(id, categories);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<DishDto> deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
