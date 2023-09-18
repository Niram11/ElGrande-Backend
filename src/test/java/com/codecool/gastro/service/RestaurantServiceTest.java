package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class RestaurantServiceTest {
    @InjectMocks
    RestaurantService service;

    @Mock
    RestaurantRepository repository;

    @Mock
    RestaurantMapper mapper;

    @Test
    void testGetRestaurant_ShouldReturnEmptyList_WhenNoRestaurant() {
        // when
        when(repository.findAll()).thenReturn(List.of());

        // then
        List<RestaurantDto> list = service.getRestaurants();

        // test
        assertEquals(0, list.size());
    }

    @Test
    void testGetRestaurants_ShouldReturnListOfRestaurantDto_WhenRestaurantsExist() {
        // given
        Restaurant restaurantOne = new Restaurant();
        restaurantOne.setId(UUID.randomUUID());
        restaurantOne.setName("Kacper");
        restaurantOne.setDescription("KacperT");
        restaurantOne.setWebsite("Kacper.com");
        restaurantOne.setContactNumber(123123123);
        restaurantOne.setContactEmail("kacper@wp.pl");

        Restaurant restaurantTwo = new Restaurant();
        restaurantOne.setId(UUID.randomUUID());
        restaurantTwo.setName("Tomek");
        restaurantTwo.setDescription("TomekK");
        restaurantTwo.setWebsite("Tomek.com");
        restaurantTwo.setContactNumber(789789789);
        restaurantTwo.setContactEmail("tomek@gmail.com");

        RestaurantDto restaurantDtoOne = new RestaurantDto(
                restaurantOne.getId(),
                restaurantOne.getName(),
                restaurantOne.getDescription(),
                restaurantOne.getWebsite(),
                restaurantOne.getContactNumber(),
                restaurantOne.getContactEmail()
        );

        RestaurantDto restaurantDtoTwo = new RestaurantDto(
                restaurantTwo.getId(),
                restaurantTwo.getName(),
                restaurantTwo.getDescription(),
                restaurantTwo.getWebsite(),
                restaurantTwo.getContactNumber(),
                restaurantTwo.getContactEmail()
        );

        // when
        when(repository.findAll()).thenReturn(List.of(restaurantOne, restaurantTwo));
        when(mapper.toDto(restaurantOne)).thenReturn(restaurantDtoOne);
        when(mapper.toDto(restaurantTwo)).thenReturn(restaurantDtoTwo);

        // then
        List<RestaurantDto> restaurantList = service.getRestaurants();

        // test
        assertEquals(List.of(restaurantDtoOne, restaurantDtoTwo), restaurantList);

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(restaurantOne);
        verify(mapper, times(1)).toDto(restaurantTwo);
    }

    @Test
    void testGetRestaurantById_ShouldReturnRestaurantDto_WhenCalledWithValidUUID() {
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
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(restaurant));
        when(mapper.toDto(any(Restaurant.class))).thenReturn(restaurantDto);

        // test
        assertEquals(restaurant.getId(), service.getRestaurantById(restaurant.getId()).id());
    }

    @Test
    void testGetRestaurantById_ShouldThrowObjectNotFoundException_WhenCalledWithInvalidUUID() {
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
        verify(repository, times(1)).save(restaurant);
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
}
