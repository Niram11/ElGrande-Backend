package com.codecool.gastro.service;

import com.codecool.gastro.dto.form.NewFormRestaurantDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest {
    @InjectMocks
    private FormService service;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;
    @Mock
    private BusinessHourRepository businessHourRepository;
    @Mock
    private BusinessHourMapper businessHourMapper;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;

    @Test
    void testProvideRestaurantForm_ShouldMapAllFieldsAndAssignValuesToThem_WhenCalled() {
        // TODO: tests
    }
}