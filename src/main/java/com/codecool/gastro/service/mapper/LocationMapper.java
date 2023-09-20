package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.entity.Location;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto toDto(Location location);

    Location dtoToLocation(NewLocationDto newLocationDto);

    Location dtoToLocation(UUID id);

    Location dtoToLocation(NewLocationDto newLocationsDto, UUID id);
}