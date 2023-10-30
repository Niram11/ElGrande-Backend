package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LocationMapperTest {

    private final LocationMapper mapper = Mappers.getMapper(LocationMapper.class);
    private final static UUID LOCATION_ID = UUID.randomUUID();
    private final static BigDecimal LATITUDE = new BigDecimal("52.5200");
    private final static BigDecimal LONGITUDE = new BigDecimal("13.4050");

    @Test
    void testMappingLocationToDtoShouldMapLocationToDtoWhenProvidingValidData() {
        // Given
        Restaurant restaurants = new Restaurant();

        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setLatitude(LATITUDE);
        location.setLongitude(LONGITUDE);
        location.assignRestaurant(restaurants);

        // When
        LocationDto locationDto = mapper.toDto(location);

        // Then
        assertEquals(locationDto.id(), LOCATION_ID);
        assertEquals(locationDto.latitude(), LATITUDE);
        assertEquals(locationDto.longitude(), LONGITUDE);
    }

    @Test
    void testMappingDtoToLocationShouldMapDtoToLocationWhenProvidingValidData() {
        // Given
        NewLocationDto newLocationDto = new NewLocationDto(LATITUDE, LONGITUDE);

        // When
        Location location = mapper.dtoToLocation(newLocationDto);

        // Then
        assertNull(location.getId());
        assertEquals(location.getLatitude(), newLocationDto.latitude());
        assertEquals(location.getLongitude(), newLocationDto.longitude());
    }

    @Test
    void testMappingDtoToLocationWithIdShouldMapDtoToLocationWithIdWhenProvidingValidData() {
        // When
        Location location = mapper.dtoToLocation(LOCATION_ID);

        // Then
        assertEquals(location.getId(), LOCATION_ID);
        assertNull(location.getLatitude());
        assertNull(location.getLongitude());
    }
}
