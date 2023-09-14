package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurant(@PathVariable UUID id) {
        return restaurantService.getRestaurantBy(id);
    }
    @GetMapping("/{id}/business-hours")
    public RestaurantDto getRestaurantBusinessHours(@PathVariable UUID id) {
        return restaurantService.getRestaurantBy(id);
    }

    @PostMapping
    public RestaurantDto createNewRestaurant(@Valid @RequestBody NewRestaurantDto newRestaurantDto) {
        return restaurantService.saveNewRestaurant(newRestaurantDto);
    }

    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(@PathVariable UUID id, @Valid @RequestBody NewRestaurantDto newRestaurantDto) {
        return restaurantService.updateRestaurant(id, newRestaurantDto);
    }

    @DeleteMapping("/{id}")
    public void softDeleteRestaurant(@PathVariable UUID id) {
        restaurantService.softDelete(id);
    }
}
