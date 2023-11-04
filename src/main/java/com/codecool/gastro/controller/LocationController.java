package com.codecool.gastro.controller;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationsService;

    public LocationController(LocationService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.status(HttpStatus.OK).body(locationsService.getAllLocations());
    }

    @PostMapping
    public ResponseEntity<LocationDto> createNewLocation(@Valid @RequestBody NewLocationDto newLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationsService.saveLocation(newLocationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID id, @Valid @RequestBody NewLocationDto newLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationsService.updateLocation(id, newLocationDTO));
    }

    // todo if location exist then add address restaurant to it instead of creating a new one
    @PutMapping("/{id}/restaurants")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocationDto> addRestaurantsToLocation(@PathVariable UUID id,
                                                                @Valid @RequestBody Set<RestaurantDto> restaurants) {
        locationsService.assignRestaurantToLocation(id, restaurants);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationDto> deleteLocations(@PathVariable UUID id) {
        locationsService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}