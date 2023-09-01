package com.codecool.gastro.controller;

import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.service.RestaurantMenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/restaurant-menus")
public class RestaurantMenuController {

    private final RestaurantMenuService restaurantMenuService;

    public RestaurantMenuController(RestaurantMenuService restaurantMenuService) {
        this.restaurantMenuService = restaurantMenuService;
    }

    @PostMapping
    public RestaurantMenuDto createRestaurantMenu(@Valid @RequestBody NewRestaurantMenuDto newRestaurantMenu){
        return restaurantMenuService.saveNewRestaurantMenu(newRestaurantMenu);
    }

    @GetMapping
    public List<RestaurantMenuDto> getAllMenus(){
        return restaurantMenuService.getAllMenus();
    }

    @PutMapping("/{restaurantMenuId}/ingredients/{ingredientId}")
    public void assignIngredientToMenu(@PathVariable UUID restaurantMenuId, @PathVariable UUID ingredientId){
        restaurantMenuService.assignIngredientToMenu(restaurantMenuId, ingredientId);
    }
}
