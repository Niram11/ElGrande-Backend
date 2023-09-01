package com.codecool.gastro.service;

import com.codecool.gastro.DTO.location.LocationDTO;
import com.codecool.gastro.DTO.location.NewLocationDTO;
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

    public List<LocationDTO> getLocations() {
        return locationRepository.findAll().stream().map(locationMapper::locationToDTO).toList();
    }

    public LocationDTO getLocationByUUID(UUID id) {
        return locationRepository.findById(id).map(locationMapper::locationToDTO)
                .orElseThrow(() -> new RuntimeException());
    }

    public LocationDTO saveLocation(NewLocationDTO newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.DTOToLocation(newLocationsDTO));
        return locationMapper.locationToDTO(savedLocations);
    }

    public LocationDTO updateLocation(UUID id, NewLocationDTO newLocationsDTO) {
        Location savedLocations = locationRepository.save(locationMapper.DTOToLocation(newLocationsDTO, id));
        return locationMapper.locationToDTO(savedLocations);
    }

    public void deleteLocation(UUID id) {
        Location deletedLocations = locationMapper.DTOToLocation(id);
        locationRepository.delete(deletedLocations);
    }
}

