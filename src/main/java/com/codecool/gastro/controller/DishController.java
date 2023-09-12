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
@RequestMapping("api/v1/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishDto> getAllRestaurantMenus() {
        return dishService.getAllDishes();
    }

    @GetMapping("/{id}")
    public DishDto getRestaurantMenu(@PathVariable UUID id) {
        return dishService.getDishBy(id);
    }

    @GetMapping
    public List<DishDto> getDishesByRestaurant(@RequestBody UUID id) {
        return dishService.getDishesByRestaurant(id);
    }

    @PostMapping
    public DishDto createNewRestaurantMenu(@Valid @RequestBody NewDishDto newRestaurantMenu) {
        return dishService.saveNewDish(newRestaurantMenu);
    }

    @PutMapping("/{id}")
    public DishDto updateRestaurantMenu(@PathVariable UUID id, @Valid @RequestBody NewDishDto newDishDto) {
        return dishService.updateDish(id, newDishDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantMenu(@PathVariable UUID id) {
        dishService.deleteDish(id);
    }

    @PutMapping("/{id}/ingredients")
    public void assignIngredientToMenu(@PathVariable UUID id, @RequestBody Set<NewIngredientDto> ingredients) {
        dishService.assignIngredientToMenu(id, ingredients);
    }
    @PutMapping("/{id}/menu-categories")
    public void assignMenuCategoriesToMenu(@PathVariable UUID id, @RequestBody Set<NewDishCategoryDto> categories) {
        dishService.assignMenuCategoryToMenu(id, categories);
    }

}
