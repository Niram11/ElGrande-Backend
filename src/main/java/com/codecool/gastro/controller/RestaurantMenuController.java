package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ingredient.NewIngredientDto;
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

    @PostMapping
    public RestaurantMenuDto createRestaurantMenu(@Valid @RequestBody NewRestaurantMenuDto newRestaurantMenu) {
        return restaurantMenuService.saveNewRestaurantMenu(newRestaurantMenu);
    }

    @GetMapping
    public List<RestaurantMenuDto> getAllMenus() {
        return restaurantMenuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public RestaurantMenuDto getMenuById(@PathVariable UUID id) {
        return restaurantMenuService.getRestaurantMenuById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable UUID id) {
        restaurantMenuService.deleteMenu(id);
    }


    //TODO: (Sebgaw12 improve endpoint "to list/from list")
    @PutMapping("/{restaurantMenuId}/ingredients")
    public void assignIngredientToMenu(@PathVariable UUID restaurantMenuId, @RequestBody Set<NewIngredientDto> ingredients) { // zamiast uuid zrobić wybieranie z listy
        restaurantMenuService.assignIngredientToMenu(restaurantMenuId, ingredients);
    }

    @PutMapping("/{id}")
    public void updateMenu(@PathVariable UUID id) {

    }
}
