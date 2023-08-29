package com.codecool.gastro.controller;

import com.codecool.gastro.controller.dto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.RestaurantMenuDto;
import com.codecool.gastro.service.RestaurantMenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/restaurant-menu")
public class RestaurantMenuController {

    private final RestaurantMenuService restaurantMenuService;

    public RestaurantMenuController(RestaurantMenuService restaurantMenuService) {
        this.restaurantMenuService = restaurantMenuService;
    }

    @PostMapping
    public RestaurantMenuDto createRestaurantMenu( @RequestBody NewRestaurantMenuDto newRestaurantMenu){
        return restaurantMenuService.saveNewRestaurantMenu(newRestaurantMenu);
    }
}
