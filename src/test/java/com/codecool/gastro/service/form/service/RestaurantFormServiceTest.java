package com.codecool.gastro.service.form.service;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.service.form.handler.FormHandler;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantFormServiceTest {
    @InjectMocks
    RestaurantFormService restaurantFormService;
    @Mock
    RestaurantMapper restaurantMapper;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    LocationRepository locationRepository;
    @Mock
    BusinessHourRepository businessHourRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    FormHandler<Address, NewAddressDto> addressHandler;
    @Mock
    FormHandler<BusinessHour, NewBusinessHourDto> businessHourHandler;
    @Mock
    FormHandler<Location, NewLocationDto> locationHandler;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testSubmitRestaurantForm_Should() {
    }
}
