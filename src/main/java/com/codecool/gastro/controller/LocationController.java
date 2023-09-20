package com.codecool.gastro.controller;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationsService;

    public LocationController(LocationService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocation() {
        return ResponseEntity.status(HttpStatus.OK).body(locationsService.getLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(locationsService.getLocationBy(id));
    }

    @PostMapping
    public ResponseEntity<LocationDto> createNewLocation(@Valid @RequestBody NewLocationDto newLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationsService.saveLocation(newLocationDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID id, @Valid @RequestBody NewLocationDto newLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationsService.updateLocation(id, newLocationDTO));
    }

    @PutMapping ("/{id}/restaurants")
    public ResponseEntity<LocationDto> addRestaurantsToLocation(@PathVariable UUID id, @Valid @RequestBody Set<RestaurantDto> restaurants ){
        locationsService.assignRestaurantToLocation(id, restaurants);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDto> deleteLocations(@PathVariable UUID id) {
        locationsService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}