package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Location;
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
        return locationRepository.findAll().stream().map(locationMapper::locationToDto).toList();
    }

    public LocationDto getLocationByUUID(UUID id) {
        return locationRepository.findById(id).map(locationMapper::locationToDto)
                .orElseThrow(() -> new RuntimeException());
    }

    public LocationDto saveLocation(NewLocationDto newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.DtoToLocation(newLocationsDTO));
        return locationMapper.locationToDto(savedLocations);
    }

    public LocationDto updateLocation(UUID id, NewLocationDto newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.DtoToLocation(newLocationsDTO, id));
        return locationMapper.locationToDto(savedLocations);
    }

    public void deleteLocation(UUID id) {
        Location deletedLocations = locationMapper.DtoToLocation(id);
        locationRepository.delete(deletedLocations);
    }
}