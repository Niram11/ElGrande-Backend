package com.codecool.gastro.controller;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.service.DishService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("api/v1/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishDto> getDishes() {
        return dishService.getAllDishes();
    }

    @GetMapping("/{id}")
    public DishDto getDish(@PathVariable UUID id) {
        return dishService.getDishBy(id);
    }

    @GetMapping(params = {"restaurantId"})
    public List<DishDto> getDishesByRestaurant(@RequestParam("restaurantId") UUID restaurantId) {
        return dishService.getDishesByRestaurant(restaurantId);
    }

    @PostMapping
    public DishDto createNewDish(@Valid @RequestBody NewDishDto newDishDto) {
        return dishService.saveNewDish(newDishDto);
    }

    @PutMapping("/{id}")
    public DishDto updateDish(@PathVariable UUID id, @Valid @RequestBody NewDishDto newDishDto) {
        return dishService.updateDish(id, newDishDto);
    }

    @PutMapping("/{id}/ingredients")
    public void assignIngredientToDish(@PathVariable UUID id, @Valid @RequestBody Set<NewIngredientDto> ingredients) {
        dishService.assignIngredientToDish(id, ingredients);
    }

    @PutMapping("/{id}/dish-categories")
    public void assignDishCategoryToDish(@PathVariable UUID id, @Valid @RequestBody Set<NewDishCategoryDto> categories) {
        dishService.assignDishCategoryToDish(id, categories);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
    }

}
