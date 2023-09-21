package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private RestaurantService service;

    @Mock
    private RestaurantRepository repository;

    @Mock
    private RestaurantMapper mapper;


    private UUID restaurantId;
    private RestaurantDto restaurantDto;
    private Restaurant restaurant;
    @BeforeEach
    void setUp() {
        restaurantId = UUID.fromString("6f2dd202-21c2-45a7-bc16-10b62f8173ab");

        restaurantDto = new RestaurantDto(
                restaurantId,
                "Tomek",
                "test2",
                "test.pl",
                321,
                "test@wp.pl"
        );

        restaurant = new Restaurant();
    }

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
        RestaurantDto restaurantDtoOne = restaurantDto;

        RestaurantDto restaurantDtoTwo = new RestaurantDto(
                UUID.randomUUID(),
                "Kacper",
                "test",
                "test.www",
                123,
                "test@gmial.com"
        );

        Restaurant restaurantOne = restaurant;
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
        restaurant.setId(restaurantId);

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

        // when
        when(mapper.dtoToRestaurant(newRestaurantDto)).thenReturn(restaurant);
        when(repository.save(restaurant)).thenReturn(restaurant);
        when(mapper.toDto(restaurant)).thenReturn(restaurantDto);

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
        restaurant.setId(restaurantId);
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
    void testGetDetailedRestaurants_ShouldReturnListOfDetailedRestaurantDto_WhenCalled() {
        // given
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name"));

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
        when(repository.findAllDetailedRestaurants(pageable)).thenReturn(List.of(restaurantOne, restaurantTwo));
        when(mapper.toDetailedDto(restaurantOne)).thenReturn(restaurantDtoOne);
        when(mapper.toDetailedDto(restaurantTwo)).thenReturn(restaurantDtoTwo);

        // then
        List<DetailedRestaurantDto> list = service.getDetailedRestaurants(pageable);

        // test
        assertEquals(2, list.size());
    }
}
