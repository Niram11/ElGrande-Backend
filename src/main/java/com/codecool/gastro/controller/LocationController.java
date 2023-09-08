package com.codecool.gastro.controller;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationsService;

    public LocationController(LocationService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public List<LocationDto> getAllLocation() {
        return locationsService.getLocations();
    }

    @GetMapping("/{id}")
    public LocationDto getLocation(@PathVariable UUID id) {
        return locationsService.getLocationBy(id);
    }

    @PostMapping
    public LocationDto createNewLocation(@Valid @RequestBody NewLocationDto newLocationDTO) {
        return locationsService.saveLocation(newLocationDTO);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable UUID id, @Valid @RequestBody NewLocationDto newLocationDTO) {
        return locationsService.updateLocation(id, newLocationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLocations(@PathVariable UUID id) {
        locationsService.deleteLocation(id);
    }
}