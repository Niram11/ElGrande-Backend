package com.codecool.gastro.service;
import com.codecool.gastro.DTO.locations.LocationsDTO;
import com.codecool.gastro.DTO.locations.NewLocationsDTO;
import com.codecool.gastro.repository.LocationsRepository;
import com.codecool.gastro.repository.entity.Locations;
import com.codecool.gastro.service.mapper.LocationsMapper;
import jakarta.persistence.EntityNotFoundException;
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
        return locationsRepository.findAll().stream().map(locationsMapper::locationsToDTO).toList();
    }

    public LocationsDTO getLocationByUUID(UUID id) {
        return locationsRepository.findById(id).map(locationsMapper::locationsToDTO)
                .orElseThrow(() -> new EntityNotFoundException(id, Locations.class));
    }

    public LocationsDTO saveLocation(NewLocationsDTO newLocationsDTO) {
        Locations savedLocations = locationsRepository.save(locationsMapper.DTOToLocations(newLocationsDTO));
        return locationsMapper.locationsToDTO(savedLocations);
    }


    public LocationsDTO updateLocation(UUID id, NewLocationsDTO newLocationsDTO) {
        Locations savedLocations = locationsRepository.save(locationsMapper.DTOToLocations(newLocationsDTO, id));
        return locationsMapper.locationsToDTO(savedLocations);
    }

    public void deleteLocation(UUID id) {
        Locations deletedLocations = locationsMapper.DTOToLocations(id);
        locationsRepository.delete(deletedLocations);
    }
}

