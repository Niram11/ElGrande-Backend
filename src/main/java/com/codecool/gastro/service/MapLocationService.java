package com.codecool.gastro.service;

import com.codecool.gastro.dto.maplocation.MapLocationDto;
import com.codecool.gastro.dto.maplocation.NewMapLocationDto;
import com.codecool.gastro.repository.MapLocationRepository;
import com.codecool.gastro.repository.entity.MapLocation;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.MapLocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MapLocationService {
    private final MapLocationRepository mapLocationRepository;
    private final MapLocationMapper mapLocationMapper;

    public MapLocationService(MapLocationRepository mapLocationRepository, MapLocationMapper mapLocationMapper) {
        this.mapLocationRepository = mapLocationRepository;
        this.mapLocationMapper = mapLocationMapper;
    }

    public List<MapLocationDto> getMapLocations() {
        return mapLocationRepository.findALl().stream()
                .map(mapLocationMapper::mapLocationToDto)
                .toList();
    }

    public MapLocationDto getMapLocationBy(UUID id) {
        return mapLocationRepository.findOneBy(id)
                .map(mapLocationMapper::mapLocationToDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, MapLocation.class));
    }

    public MapLocationDto saveNewMapLocation(NewMapLocationDto newMapLocationDto) {
        MapLocation savedMapLocation = mapLocationRepository.save(mapLocationMapper.dtoToMapLocation(newMapLocationDto));
        return mapLocationMapper.mapLocationToDto(savedMapLocation);
    }

    public MapLocationDto updateMapLocation(UUID id, NewMapLocationDto newMapLocationDto) {
        MapLocation updatedMapLocation = mapLocationRepository.save(mapLocationMapper.dtoToMapLocation(newMapLocationDto, id));
        return mapLocationMapper.mapLocationToDto(updatedMapLocation);
    }

    public void deleteMapLocation(UUID id) {
        mapLocationRepository.delete(mapLocationMapper.dtoToMapLocation(id));
    }

}
