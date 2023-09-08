package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
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

    public void deleteLocation(UUID id) {
        locationRepository.delete(locationMapper.dtoToLocation(id));
    }
}