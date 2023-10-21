package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    void testFindById_ShouldReturnRestaurant_WhenExist() {
        // given
        UUID id = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // when
        Optional<Restaurant> restaurant = repository.findById(id);

        // then
        assertTrue(restaurant.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoRestaurant() {
        // given
        UUID id = UUID.randomUUID();

        // when
        Optional<Restaurant> restaurant = repository.findById(id);

        // then
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

        // then
        assertTrue(afterDeletionRestaurant.isEmpty());
    }


    @Test
    void testFindAllDetailedRestaurants_ShouldReturnListOfDetailedRestaurantProjection_WhenExist() {
        // when
        List<DetailedRestaurantProjection> projection = repository.findAllDetailedRestaurants(PageRequest.of(0, 3, Sort.by("name")));

        // then
        assertEquals(3, projection.size());
    }

}
