package com.codecool.gastro.service.mapper;


import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.entity.Location;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LocationMapper {

//    @Mapping(target = "restaurant", source = "location.restaurant.id")
    LocationDto locationToDto(Location locations);

//    @Mapping(target = "location.restaurant.id", source = "restaurant")
    Location DtoToLocation(NewLocationDto newLocationDto);

    Location DtoToLocation(UUID id);

    Location DtoToLocation(NewLocationDto newLocationsDto, UUID id);
}
