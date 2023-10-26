package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocationValidation implements Validation<UUID>{
    private final LocationRepository locationRepository;

    public LocationValidation(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void validateEntityById(UUID id) {
        locationRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Location.class));
    }
}
