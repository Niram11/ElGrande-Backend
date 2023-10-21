package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessHourFormHandlerTest {
    FormHandler<BusinessHour> formHandler;
    @Mock
    BusinessHourMapper businessHourMapper;
    @Mock
    BusinessHourRepository businessHourRepository;

    private BusinessHour businessHour;
    private Restaurant restaurant;
    private NewRestaurantFormDto formDto;

    @BeforeEach
    void setUp() {
        formHandler = new BusinessHourFormHandler(
                businessHourMapper,
                businessHourRepository
        );
        NewBusinessHourDto newBusinessHourDto = new NewBusinessHourDto(
                1,
                null,
                null
        );

        formDto = new NewRestaurantFormDto(
                null,
                null,
                List.of(newBusinessHourDto),
                null
        );

        businessHour = new BusinessHour();
        restaurant = new Restaurant();
    }

    @Captor
    private ArgumentCaptor<List<BusinessHour>> captor;

    @Test
    void testHandleRestaurantForm_ShouldCreateNewAddressAndSetRestaurantToIt_WhenCalled() {
        // when
        when(businessHourMapper.dtoToBusinessHour(any(NewBusinessHourDto.class))).thenReturn(businessHour);
        formHandler.handleRestaurantForm(formDto, restaurant);

        // then
        verify(businessHourRepository, times(1)).saveAll(captor.capture());
        assertNotNull(captor.getValue().get(0).getRestaurant());
    }
}
