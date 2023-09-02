package com.codecool.gastro.controller;

import com.codecool.gastro.dto.maplocation.MapLocationDto;
import com.codecool.gastro.dto.maplocation.NewMapLocationDto;
import com.codecool.gastro.repository.entity.MapLocation;
import com.codecool.gastro.service.MapLocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/map-locations")
public class MapLocationController {
    private final MapLocationService mapLocationService;

    public MapLocationController(MapLocationService mapLocationService) {
        this.mapLocationService = mapLocationService;
    }

    @GetMapping
    public List<MapLocationDto> getAllMapLocations() {
        return mapLocationService.getMapLocations();
    }

    @GetMapping("/{id}")
    public MapLocationDto getMapLocation(@PathVariable UUID id) {
        return mapLocationService.getMapLocationBy(id);
    }

    @PostMapping
    public MapLocationDto createNewMapLocation(@Valid @RequestBody NewMapLocationDto newMapLocationDto) {
        return mapLocationService.saveNewMapLocation(newMapLocationDto);
    }

    @PutMapping("/{id}")
    public MapLocationDto updateMapLocation(@PathVariable UUID id, @Valid @RequestBody NewMapLocationDto newMapLocationDto) {
        return mapLocationService.updateMapLocation(id , newMapLocationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMapLocation(@PathVariable UUID id) {
        mapLocationService.deleteMapLocation(id);
    }
}
