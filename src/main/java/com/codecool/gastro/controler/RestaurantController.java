package com.codecool.gastro.controler;

import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantService.getRestaurants();
    }

    @PostMapping
    public RestaurantDTO createNewRestaurant(@Valid @RequestBody NewRestaurantDTO newRestaurantDTO) {
        return restaurantService.saveNewRestaurant(newRestaurantDTO);
    }
}
