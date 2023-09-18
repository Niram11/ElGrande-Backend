package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.getRestaurantById(id));
    }

    @GetMapping(params = "top")
    public List<DetailedRestaurantDto> getTopRestaurantsDetailed(@RequestParam("top") int quantity) {
        return restaurantService.getTopRestaurants(quantity);
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> createNewRestaurant(@Valid @RequestBody NewRestaurantDto newRestaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.saveNewRestaurant(newRestaurantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable UUID id, @Valid @RequestBody NewRestaurantDto newRestaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.updateRestaurant(id, newRestaurantDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestaurantDto> softDeleteRestaurant(@PathVariable UUID id) {
        restaurantService.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
