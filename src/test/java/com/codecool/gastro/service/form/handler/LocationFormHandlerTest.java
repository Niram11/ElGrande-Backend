package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.mapper.LocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationFormHandlerTest {
    FormHandler<Location> formHandler;
    @Mock
    LocationMapper locationMapper;
    @Mock
    LocationRepository locationRepository;

    private Location location;
    private Restaurant restaurant;
    private NewRestaurantFormDto formDto;

    @BeforeEach
    void setUp() {
        formHandler = new LocationFormHandler(
                locationMapper,
                locationRepository
        );

        NewLocationDto newAddressDto = new NewLocationDto(
                null,
                null
        );

        formDto = new NewRestaurantFormDto(
                null,
                newAddressDto,
                null,
                null
        );

        location = new Location();
        restaurant = new Restaurant();
    }

    @Captor
    private ArgumentCaptor<Location> captor;

    @Test
    void testHandleRestaurantForm_ShouldCreateNewLocationAndSetRestaurantToIt_WhenCalled() {
        // when
        when(locationMapper.dtoToLocation(any(NewLocationDto.class))).thenReturn(location);
        formHandler.handleRestaurantForm(formDto, restaurant);

        // then
        verify(locationRepository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getRestaurants().size(), 1);
    }
}
