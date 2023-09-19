package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    @InjectMocks
    RestaurantService service;

    @Mock
    RestaurantRepository repository;

    @Mock
    RestaurantMapper mapper;

    @Test
    void testGetRestaurants_ShouldReturnEmptyList_WhenNoRestaurant() {
        // when
        when(repository.findAll()).thenReturn(List.of());

        // then
        List<RestaurantDto> list = service.getRestaurants();

        // test
        assertEquals(0, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetRestaurants_ShouldReturnListOfRestaurantDto_WhenRestaurantsExist() {
        // given
        RestaurantDto restaurantDtoOne = new RestaurantDto(
                UUID.randomUUID(),
                "Tomek",
                "test2",
                "test.pl",
                321,
                "test@wp.pl"
        );

        RestaurantDto restaurantDtoTwo = new RestaurantDto(
                UUID.randomUUID(),
                "Kacper",
                "test",
                "test.www",
                123,
                "test@gmial.com"
        );

        Restaurant restaurantOne = new Restaurant();
        Restaurant restaurantTwo = new Restaurant();

        // when
        when(repository.findAll()).thenReturn(List.of(restaurantOne, restaurantTwo));
        when(mapper.toDto(restaurantOne)).thenReturn(restaurantDtoOne);
        when(mapper.toDto(restaurantTwo)).thenReturn(restaurantDtoTwo);

        // then
        List<RestaurantDto> restaurantList = service.getRestaurants();

        // test
        assertEquals(List.of(restaurantDtoOne, restaurantDtoTwo), restaurantList);

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(Restaurant.class));
    }

    @Test
    void testGetRestaurantById_ShouldReturnRestaurantDto_WhenRestaurantExist() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());

        RestaurantDto restaurantDto = new RestaurantDto(
                restaurant.getId(),
                null,
                null,
                null,
                null,
                null
        );

        // when
        when(repository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(mapper.toDto(any(Restaurant.class))).thenReturn(restaurantDto);

        // test
        assertEquals(restaurant.getId(), service.getRestaurantById(restaurant.getId()).id());
        verify(repository, times(1)).findById(any(UUID.class));
        verify(mapper, times(1)).toDto(any(Restaurant.class));
    }

    @Test
    void testGetRestaurantById_ShouldThrowObjectNotFoundException_WhenNoRestaurant() {
        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getRestaurantById(UUID.randomUUID()));
    }

    @Test
    void testSaveNewRestaurantAndUpdateRestaurant_ShouldReturnRestaurantDto_WhenSavedAnInstance() {

        // given
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto(
                "Kacper",
                "KacperT",
                "www.pl",
                123123123,
                "kacper@wp.pl"
        );

        UUID restaurantId = UUID.randomUUID();

        RestaurantDto restaurantDto = new RestaurantDto(
                restaurantId,
                newRestaurantDto.name(),
                newRestaurantDto.description(),
                newRestaurantDto.website(),
                newRestaurantDto.contactNumber(),
                newRestaurantDto.contactEmail()
        );

        Restaurant restaurant = new Restaurant();
        restaurant.setName(newRestaurantDto.name());
        restaurant.setDescription(newRestaurantDto.description());
        restaurant.setWebsite(newRestaurantDto.website());
        restaurant.setContactNumber(newRestaurantDto.contactNumber());
        restaurant.setContactEmail(newRestaurantDto.contactEmail());

        // when
        when(mapper.dtoToRestaurant(any(NewRestaurantDto.class))).thenReturn(restaurant);
        when(repository.save(any(Restaurant.class))).thenReturn(restaurant);
        when(mapper.toDto(any(Restaurant.class))).thenReturn(restaurantDto);

        // then
        RestaurantDto savedNewRestaurant = service.saveNewRestaurant(newRestaurantDto);

        // test
        assertEquals(savedNewRestaurant, restaurantDto);
        verify(mapper, times(1)).dtoToRestaurant(any(NewRestaurantDto.class));
        verify(repository, times(1)).save(any(Restaurant.class));
        verify(mapper, times(1)).toDto(any(Restaurant.class));
    }

    @Captor
    ArgumentCaptor<Restaurant> captor;

    @Test
    void testSoftDelete_ShouldReturnObfuscatedData_WhenSavingRestaurant() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Kacper");
        restaurant.setDescription("KacperT");
        restaurant.setWebsite("Kacper.com");
        restaurant.setContactNumber(123123123);
        restaurant.setContactEmail("kacper@wp.pl");

        // when
        when(repository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        // then
        service.softDelete(restaurant.getId());

        // test
        verify(repository, times(1)).save(captor.capture());
        verify(repository, times(1)).findById(restaurant.getId());
        assertEquals(captor.getValue().getName(), "*".repeat(restaurant.getName().length()));
        assertEquals(captor.getValue().getDescription(), "*".repeat(restaurant.getDescription().length()));
        assertEquals(captor.getValue().getWebsite(), "*".repeat(restaurant.getWebsite().length()));
        assertEquals(captor.getValue().getContactNumber(), 0);
        assertTrue(captor.getValue().getDeleted());
        assertNotEquals(captor.getValue().getContactEmail(), "kacper@wp.pl");
    }

    @Test
    void testSoftDelete_ShouldThrowObjectNotFoundException_WhenSavingNotExistingRestaurant() {
        // test
        assertThrows(ObjectNotFoundException.class, () -> service.softDelete(UUID.randomUUID()));
    }

    @Test
    void testGetTopRestaurants_ShouldReturnListOfDetailedRestaurantDto_WhenCalled() {
        // given
        int quantity = 2;

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

        DetailedRestaurantProjection restaurantOne = factory.createProjection(DetailedRestaurantProjection.class);
        DetailedRestaurantProjection restaurantTwo = factory.createProjection(DetailedRestaurantProjection.class);

        DetailedRestaurantDto restaurantDtoOne = new DetailedRestaurantDto(
                UUID.randomUUID(),
                "Name1",
                "Desc1",
                "Website1",
                321321321,
                "Email@wp.pl1",
                new String[]{"image1", "image2"},
                BigDecimal.valueOf(9.3)
        );

        DetailedRestaurantDto restaurantDtoTwo = new DetailedRestaurantDto(
                UUID.randomUUID(),
                "Name2",
                "Desc2",
                "Website2",
                123123123,
                "Email@wp.pl2",
                new String[]{"image3", "image4"},
                BigDecimal.valueOf(9.3)
        );

        // when
        when(repository.getTopRestaurants(quantity)).thenReturn(List.of(restaurantOne, restaurantTwo));
        when(mapper.toDetailedDto(restaurantOne)).thenReturn(restaurantDtoOne);
        when(mapper.toDetailedDto(restaurantTwo)).thenReturn(restaurantDtoTwo);

        // then
        List<DetailedRestaurantDto> list = service.getTopRestaurants(2);

        // test
        assertEquals(2, list.size());
    }
}
