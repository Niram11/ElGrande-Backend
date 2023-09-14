package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper,
                           RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public List<LocationDto> getLocations() {
        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toDto)
                .toList();
    }

    public LocationDto getLocationBy(UUID id) {
        return locationRepository.findById(id)
                .map(locationMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Location.class));
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
        for (RestaurantDto restaurant : restaurants) {

            Optional<Restaurant> selectedRestaurant = restaurantRepository.findById(restaurant.id());
            Restaurant mappedRestaurant = restaurantMapper.dtoToRestaurant(selectedRestaurant);

            if (selectedRestaurant.isEmpty()) {
                restaurantRepository.save(mappedRestaurant);
                location.assignRestaurant(mappedRestaurant);

            } else if (!location.getRestaurants().contains(selectedRestaurant.get())) {
                location.assignRestaurant(selectedRestaurant.get());

            }
        }
    }

    public void deleteLocation(UUID id) {
        locationRepository.delete(locationMapper.dtoToLocation(id));
    }
}