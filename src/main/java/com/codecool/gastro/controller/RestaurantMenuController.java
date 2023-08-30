package com.codecool.gastro.controller;

import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.service.RestaurantMenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<RestaurantMenuDto> getAllMenus(){
        return restaurantMenuService.getAllMenus();
    }


}
