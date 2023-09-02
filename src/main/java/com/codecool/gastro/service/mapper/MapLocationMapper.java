package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.maplocation.MapLocationDto;
import com.codecool.gastro.dto.maplocation.NewMapLocationDto;
import com.codecool.gastro.repository.entity.MapLocation;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MapLocationMapper {
    MapLocationDto mapLocationToDto(MapLocation mapLocation);

    MapLocation dtoToMapLocation(NewMapLocationDto newMapLocationDto);
    MapLocation dtoToMapLocation(NewMapLocationDto newMapLocationDto, UUID id);
    MapLocation dtoToMapLocation(UUID id);
}
