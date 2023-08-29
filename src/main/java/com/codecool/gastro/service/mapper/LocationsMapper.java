package com.codecool.gastro.service.mapper;


import com.codecool.gastro.DTO.locations.LocationsDTO;
import com.codecool.gastro.DTO.locations.NewLocationsDTO;
import com.codecool.gastro.repository.entity.Locations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationsMapper {

    @Mapping(target = "restaurantId", source = "locations.restaurant.id")
    LocationsDTO locationsToDTO(Locations locations);

    @Mapping(target = "locations.restaurant.id", source = "restaurantId")
    Locations DTOToLocations(NewLocationsDTO newLocationsDTO);
}
