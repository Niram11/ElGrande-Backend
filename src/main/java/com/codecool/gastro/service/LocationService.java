package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.validation.LocationValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationValidation locationValidation;
    private final RestaurantValidation restaurantValidation;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper,
                           LocationValidation locationValidation, RestaurantValidation restaurantValidation) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.locationValidation = locationValidation;
        this.restaurantValidation = restaurantValidation;
    }

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toDto)
                .toList();
    }

    public LocationDto saveLocation(NewLocationDto newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.dtoToLocation(newLocationsDTO));
        return locationMapper.toDto(savedLocations);
    }

    public LocationDto updateLocation(UUID id, NewLocationDto newLocationDTO) {
        Location updatedLocation = locationValidation.validateEntityById(id);
        locationMapper.updateLocationFromDto(newLocationDTO, updatedLocation);
        return locationMapper.toDto(locationRepository.save(updatedLocation));
    }

    public void assignRestaurantToLocation(UUID locationId, Set<RestaurantDto> restaurantDtoSet) {
        Location updatedLocation = locationValidation.validateEntityById(locationId);
        restaurantDtoSet.forEach(r -> updatedLocation.assignRestaurant(restaurantValidation.validateEntityById(r.id())));
        locationRepository.save(updatedLocation);
    }

    public void deleteLocation(UUID id) {
        locationRepository.delete(locationMapper.dtoToLocation(id));
    }
}