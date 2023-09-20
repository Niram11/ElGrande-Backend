package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void testFindAll_ShouldReturnListOfRestaurants_WhenCallingMethod() {
        // when
        List<Restaurant> list = repository.findAll();

        // test
        assertEquals(list.size(), 3);
    }

    @Test
    void testFindById_ShouldReturnRestaurant_WhenExist() {
        // given
        UUID id = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // when
        Optional<Restaurant> restaurant = repository.findById(id);

        // test
        assertTrue(restaurant.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoRestaurant() {
        // given
        UUID id = UUID.randomUUID();

        // when
        Optional<Restaurant> restaurant = repository.findById(id);

        // test
        assertTrue(restaurant.isEmpty());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenProvidingExistingRestaurantsIdButIsDeleted() {
        // given
        UUID id = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // when
        Restaurant restaurant = repository.findById(id).get();
        restaurant.setDeleted(true);

        repository.save(restaurant);
        Optional<Restaurant> afterDeletionRestaurant = repository.findById(id);

        // test
        assertTrue(afterDeletionRestaurant.isEmpty());
    }


    @Test
    void testSave_ShouldReturnSameEntity_WhenProvidingValidData() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Kacper");
        restaurant.setDescription("Kacper");
        restaurant.setWebsite("Kacper");
        restaurant.setContactNumber(123123123);
        restaurant.setContactEmail("Kacper@wp.pl");

        // when
        Restaurant savedRestaurant = repository.save(restaurant);
        Optional<Restaurant> savedRestaurantById = repository.findById(savedRestaurant.getId());

        // test
        assertTrue(savedRestaurantById.isPresent());
        assertEquals(savedRestaurant, restaurant);
    }

    @Test
    void testSave_ShouldUpdateRestaurant_WhenProvidingExistingId() {
        // given
        UUID id = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("KacperTest");
        restaurant.setDescription("KacperTest");
        restaurant.setWebsite("KacperTest");
        restaurant.setContactNumber(123123123);
        restaurant.setContactEmail("KacperTest@wp.pl");

        // when
        Restaurant restaurantAfterSave = repository.save(restaurant);

        // test
        assertEquals(restaurantAfterSave.getId(), id);
        assertNotEquals(restaurantAfterSave.getName(), "Kacper");
        assertNotEquals(restaurantAfterSave.getDescription(), "Desc1");
        assertNotEquals(restaurantAfterSave.getWebsite(), "Kacper.pl");
        assertNotEquals(restaurantAfterSave.getContactNumber(), 123);
        assertNotEquals(restaurantAfterSave.getContactEmail(), "Kacper@wp.pl");
    }

    @Test
    void testGetTopRestaurants_ShouldReturnListOfDetailedRestaurantProjection_WhenExist() {
        // then
        List<DetailedRestaurantProjection> projection = repository.getTopRestaurants(3);

        // test
        assertEquals(3, projection.size());
    }

}
