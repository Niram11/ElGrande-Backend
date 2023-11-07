package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantFormHandlerTest {
    FormHandler<Restaurant> formHandler;
    @Mock
    RestaurantMapper restaurantMapper;
    @Mock
    RestaurantRepository restaurantRepository;

    private Restaurant restaurant;
    private NewRestaurantFormDto formDto;

    @BeforeEach
    void setUp() {
        formHandler = new RestaurantFormHandler(
                restaurantMapper,
                restaurantRepository
        );

        NewRestaurantDto newRestaurantDto = new NewRestaurantDto(
                null,
                null,
                null,
                null,
                null
        );

        formDto = new NewRestaurantFormDto(
                newRestaurantDto,
                null,
                null,
                null,
                UUID.randomUUID()
        );

        restaurant = new Restaurant();
    }

    @Test
    void testHandleRestaurantForm_ShouldUpdateRestaurant_WhenCalled() {
        // when
        formHandler.handleRestaurantForm(formDto, restaurant);

        // then
        verify(restaurantMapper, times(1))
                .updateRestaurantFromDto(any(NewRestaurantDto.class), eq(restaurant));
        verify(restaurantRepository, times(1)).save(restaurant);
    }
}
