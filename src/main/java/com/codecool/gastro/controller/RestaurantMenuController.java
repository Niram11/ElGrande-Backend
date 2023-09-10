package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.service.RestaurantMenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurant-menus")
public class RestaurantMenuController {

    private final RestaurantMenuService restaurantMenuService;

    public RestaurantMenuController(RestaurantMenuService restaurantMenuService) {
        this.restaurantMenuService = restaurantMenuService;
    }

    @GetMapping
    public List<RestaurantMenuDto> getAllRestaurantMenus() {
        return restaurantMenuService.getAllRestaurantMenus();
    }

    @GetMapping("/{id}")
    public RestaurantMenuDto getRestaurantMenu(@PathVariable UUID id) {
        return restaurantMenuService.getRestaurantMenuBy(id);
    }

    @PostMapping
    public RestaurantMenuDto createNewRestaurantMenu(@Valid @RequestBody NewRestaurantMenuDto newRestaurantMenu) {
        return restaurantMenuService.saveNewRestaurantMenu(newRestaurantMenu);
    }

    @PutMapping("/{id}")
    public RestaurantMenuDto updateRestaurantMenu(@PathVariable UUID id, @Valid @RequestBody NewRestaurantMenuDto newRestaurantMenuDto) {
        return restaurantMenuService.updateRestaurantMenu(id, newRestaurantMenuDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantMenu(@PathVariable UUID id) {
        restaurantMenuService.deleteRestaurantMenu(id);
    }

    @PutMapping("/{id}/ingredients")
    public void assignIngredientToMenu(@PathVariable UUID id, @RequestBody Set<NewIngredientDto> ingredients) {
        restaurantMenuService.assignIngredientToMenu(id, ingredients);
    }
    @PutMapping("/{id}/menu-categories")
    public void assignMenuCategoriesToMenu(@PathVariable UUID id, @RequestBody Set<NewMenuCategoryDto> categories) {
        restaurantMenuService.assignMenuCategoryToMenu(id, categories);
    }

}
