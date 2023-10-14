package com.codecool.gastro.controller;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurantById(id));
    }

    @GetMapping(params = {"page", "size", "sort"})
    public ResponseEntity<List<DetailedRestaurantDto>> getTopRestaurantsDetailed(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getDetailedRestaurants(pageable));
    }

    @GetMapping(path = "/filtered")
    public ResponseEntity<List<RestaurantDto>> getFilteredRestaurants(
            @ModelAttribute FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getFilteredRestaurants(filteredRestaurantsCriteria));
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
