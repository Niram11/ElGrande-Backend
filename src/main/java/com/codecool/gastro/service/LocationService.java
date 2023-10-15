package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final RestaurantRepository restaurantRepository;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper,
                           RestaurantRepository restaurantRepository) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public LocationDto saveLocation(NewLocationDto newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.dtoToLocation(newLocationsDTO));
        return locationMapper.toDto(savedLocations);
    }

    public LocationDto updateLocation(UUID id, NewLocationDto newLocationsDTO) {
        Location updatedLocations = locationRepository.save(locationMapper.dtoToLocation(newLocationsDTO, id));
        return locationMapper.toDto(updatedLocations);
    }

    public void assignRestaurantToLocation(UUID locationId, Set<RestaurantDto> restaurantDto) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ObjectNotFoundException(locationId, Location.class));
        addRestaurantToLocation(restaurantDto, location);
        locationRepository.save(location);
    }

    public void addRestaurantToLocation(Set<RestaurantDto> restaurants, Location location) {
        restaurants.forEach(restaurantDto -> restaurantRepository.findById(restaurantDto.id())
                .ifPresent(location::assignRestaurant));
    }

    public void deleteLocation(UUID id) {
        locationRepository.delete(locationMapper.dtoToLocation(id));
    }
}