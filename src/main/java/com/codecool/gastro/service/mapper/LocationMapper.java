package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.location.LocationDTO;
import com.codecool.gastro.DTO.location.NewLocationDTO;
import com.codecool.gastro.repository.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LocationMapper {

//    @Mapping(target = "restaurant", source = "location.restaurant.id")
    LocationDTO locationToDTO(Location locations);

//    @Mapping(target = "location.restaurant.id", source = "restaurant")
    Location DTOToLocation(NewLocationDTO newLocationDTO);

    Location DTOToLocation(UUID id);

    Location DTOToLocation(NewLocationDTO newLocationsDTO, UUID id);
}
