package com.codecool.gastro.controler;
import com.codecool.gastro.DTO.locations.LocationsDTO;
import com.codecool.gastro.DTO.locations.NewLocationsDTO;
import com.codecool.gastro.service.LocationsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationsController {
    private final LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public List<LocationsDTO> getAllLocations() {
        return locationsService.getLocations();
    }

    @GetMapping("/{id}")
    public LocationsDTO getLocations(@PathVariable UUID id) {
        return locationsService.getLocationByUUID(id);
    }

    @PostMapping
    public LocationsDTO createNewLocations(@Valid @RequestBody NewLocationsDTO newLocationsDTO) {
        return locationsService.saveLocation(newLocationsDTO);
    }

    @PutMapping("/{id}")
    public LocationsDTO updateLocations(@PathVariable UUID id, @Valid @RequestBody NewLocationsDTO newLocationsDTO) {
        return locationsService.updateLocation(id, newLocationsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLocations(@PathVariable UUID id) {
        locationsService.deleteLocation(id);
    }
}
