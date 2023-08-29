package com.codecool.gastro.service;

import com.codecool.gastro.DTO.locations.LocationsDTO;
import com.codecool.gastro.repository.LocationsRepository;
import com.codecool.gastro.repository.entity.Locations;
import com.codecool.gastro.service.mapper.LocationsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationsService {
    private final LocationsRepository locationsRepository;
    private final LocationsMapper locationsMapper;

    public LocationsService(LocationsRepository locationsRepository, LocationsMapper locationsMapper) {
        this.locationsRepository = locationsRepository;
        this.locationsMapper = locationsMapper;
    }

    public List<LocationsDTO> getLocations() {
        return locationsRepository.findAll().stream()
    }

    public LocationsDTO getLocationByUUID(UUID id) {
        return locationsRepository.findById(id).map()
    }

    public LocationsDTO saveLocation() {
        Locations locations = locationsRepository.save()
    }

    public LocationsDTO updateLocation() {
        Locations locations = locationsRepository.save()
    }

    public void deleteLocation() {
        locationsRepository.delete();
    }
}
