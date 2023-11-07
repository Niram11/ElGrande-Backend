package com.codecool.gastro.service;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.validation.LocationValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {
    @InjectMocks
    LocationService service;
    @Mock
    LocationRepository locationRepository;
    @Mock
    LocationMapper locationMapper;
    @Mock
    LocationValidation locationValidation;
    @Mock
    RestaurantValidation restaurantValidation;

    private UUID locationId;
    private LocationDto locationDto;
    private Location location;
    private NewLocationDto newLocationDto;
    private Set<RestaurantDto> restaurantDtoSet;

    @BeforeEach
    void setUp() {
        locationId = UUID.fromString("5d0c91c3-9d20-4e24-84a2-04d420b86bc0");

        locationDto = new LocationDto(
                locationId,
                new BigDecimal("52.2296756"),
                new BigDecimal("21.0122287"),
                new HashSet<>()
        );

        newLocationDto = new NewLocationDto(
                new BigDecimal("50.0646501"),
                new BigDecimal("19.9449799")
        );

        location = new Location();
        location.setId(locationId);
        location.setLatitude(new BigDecimal("52.2296756"));
        location.setLongitude(new BigDecimal("21.0122287"));

        restaurantDtoSet = new HashSet<>();
    }
    @Test
    void testSaveLocationShouldReturnLocationDto() {
        // Arrange
        when(locationMapper.dtoToLocation(newLocationDto)).thenReturn(location);
        when(locationRepository.save(location)).thenReturn(location);
        when(locationMapper.toDto(location)).thenReturn(locationDto);

        // Act
        LocationDto savedLocationDto = service.saveLocation(newLocationDto);

        // Assert
        assertEquals(locationDto, savedLocationDto);
        verify(locationRepository, times(1)).save(location);
        verify(locationMapper, times(1)).toDto(location);
    }

    @Test
    void testAssignRestaurantToLocationShouldAddRestaurantsToLocation() {
        // Arrange
        Location location = new Location();
        location.setId(locationId);

        RestaurantDto restaurantDto1 = new RestaurantDto(UUID.randomUUID(), "Restaurant 1",
                "Desc 1", "www.restaurant1.com", 123456789, "email1@example.com");
        RestaurantDto restaurantDto2 = new RestaurantDto(UUID.randomUUID(), "Restaurant 2",
                "Desc 2", "www.restaurant2.com", 987654321, "email2@example.com");
        restaurantDtoSet.add(restaurantDto1);
        restaurantDtoSet.add(restaurantDto2);

        // Act
        when(locationValidation.validateEntityById(locationId)).thenReturn(location);
        when(restaurantValidation.validateEntityById(restaurantDto1.id())).thenReturn(new Restaurant());
        when(restaurantValidation.validateEntityById(restaurantDto2.id())).thenReturn(new Restaurant());
        service.assignRestaurantToLocation(locationId, restaurantDtoSet);

        // Assert
        assertEquals(2, location.getRestaurants().size());
    }
}

