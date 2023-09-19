package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RestaurantMapperTest {

    private final RestaurantMapper mapper = Mappers.getMapper(RestaurantMapper.class);


    @Test
    void testToDto_ShouldMapRestaurantToDto_WhenProvidingValidData() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Name");
        restaurant.setDescription("Desc");
        restaurant.setWebsite("www.pl");
        restaurant.setContactNumber(123123123);
        restaurant.setContactEmail("Email@wp.pl");

        // when
        RestaurantDto restaurantDto = mapper.toDto(restaurant);

        // test
        assertEquals(restaurantDto.id(), restaurant.getId());
        assertEquals(restaurantDto.name(), restaurant.getName());
        assertEquals(restaurantDto.description(), restaurant.getDescription());
        assertEquals(restaurantDto.website(), restaurant.getWebsite());
        assertEquals(restaurantDto.contactNumber(), restaurant.getContactNumber());
        assertEquals(restaurantDto.contactEmail(), restaurant.getContactEmail());
    }

    @Test
    void testDtoToRestaurant_ShouldMapToRestaurant_WhenProvidingValidData() {
        // given
        UUID id = UUID.randomUUID();

        NewRestaurantDto newRestaurantDto = new NewRestaurantDto(
                "Name",
                "Desc",
                "www.pl",
                123123123,
                "Email@wp.pl"
        );

        // when
        Restaurant restaurantOne = mapper.dtoToRestaurant(newRestaurantDto);
        Restaurant restaurantTwo = mapper.dtoToRestaurant(newRestaurantDto, id);

        // test
        assertEquals(newRestaurantDto.name(), restaurantOne.getName());
        assertEquals(newRestaurantDto.description(), restaurantOne.getDescription());
        assertEquals(newRestaurantDto.website(), restaurantOne.getWebsite());
        assertEquals(newRestaurantDto.contactNumber(), restaurantOne.getContactNumber());
        assertEquals(newRestaurantDto.contactEmail(), restaurantOne.getContactEmail());

        assertEquals(id, restaurantTwo.getId());
        assertEquals(newRestaurantDto.name(), restaurantTwo.getName());
        assertEquals(newRestaurantDto.description(), restaurantTwo.getDescription());
        assertEquals(newRestaurantDto.website(), restaurantTwo.getWebsite());
        assertEquals(newRestaurantDto.contactNumber(), restaurantTwo.getContactNumber());
        assertEquals(newRestaurantDto.contactEmail(), restaurantTwo.getContactEmail());
    }
}
