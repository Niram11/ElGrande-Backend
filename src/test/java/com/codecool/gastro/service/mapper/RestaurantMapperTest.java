package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.math.BigDecimal;
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
        Restaurant restaurant = mapper.dtoToRestaurant(newRestaurantDto, id);

        // test

        assertEquals(id, restaurant.getId());
        assertEquals(newRestaurantDto.name(), restaurant.getName());
        assertEquals(newRestaurantDto.description(), restaurant.getDescription());
        assertEquals(newRestaurantDto.website(), restaurant.getWebsite());
        assertEquals(newRestaurantDto.contactNumber(), restaurant.getContactNumber());
        assertEquals(newRestaurantDto.contactEmail(), restaurant.getContactEmail());
    }

    @Test
    void testToDetailedDto_ShouldReturnDetailedRestaurantDto_WhenCalled() {
        // given
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

        DetailedRestaurantProjection restaurant = factory.createProjection(DetailedRestaurantProjection.class);
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Name");
        restaurant.setDescription("Desc");
        restaurant.setWebsite("website.pl");
        restaurant.setContactNumber(123123123);
        restaurant.setContactEmail("email@wp.pl");
        restaurant.setImagesPaths(new String[]{"image1", "image2"});
        restaurant.setAverageGrade(BigDecimal.valueOf(1.34));

        // then
        DetailedRestaurantDto restaurantDto = mapper.toDetailedDto(restaurant);

        // test
        assertEquals(restaurantDto.id(), restaurant.getId());
        assertEquals(restaurantDto.name(), restaurant.getName());
        assertEquals(restaurantDto.description(), restaurant.getDescription());
        assertEquals(restaurantDto.contactNumber(), restaurant.getContactNumber());
        assertEquals(restaurantDto.contactEmail(), restaurant.getContactEmail());
        assertEquals(restaurantDto.imagesPaths().length, restaurant.getImagesPaths().length);
        assertEquals(restaurantDto.averageGrade(), restaurant.getAverageGrade());
    }
}
