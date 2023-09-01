package com.codecool.gastro.controler;

import com.codecool.gastro.DTO.location.LocationDTO;
import com.codecool.gastro.DTO.location.NewLocationDTO;
import com.codecool.gastro.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationsService;

    public LocationController(LocationService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public List<LocationDTO> getAllLocation() {
        return locationsService.getLocations();
    }

    @GetMapping("/{id}")
    public LocationDTO getLocation(@PathVariable UUID id) {
        return locationsService.getLocationByUUID(id);
    }

    @PostMapping
    public LocationDTO createNewLocation(@Valid @RequestBody NewLocationDTO newLocationDTO) {
        return locationsService.saveLocation(newLocationDTO);
    }

    @PutMapping("/{id}")
    public LocationDTO updateLocation(@PathVariable UUID id, @Valid @RequestBody NewLocationDTO newLocationDTO) {
        return locationsService.updateLocation(id, newLocationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLocations(@PathVariable UUID id) {
        locationsService.deleteLocation(id);
    }
}
