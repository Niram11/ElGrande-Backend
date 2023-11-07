package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RestaurantPredicateByDishTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetFilteredRestaurantsByDishProvidingProperNameShouldReturnRestaurant() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setDescription("Test Description");
        entityManager.persist(restaurant);

        Dish dish = new Dish();
        dish.setDishName("Test Dish");
        dish.setPrice(BigDecimal.valueOf(10.99));
        dish.setRestaurant(restaurant);
        entityManager.persist(dish);

        // when
        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(null, null, null, List.of("Test Dish"));
        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());
        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        // then
        assertEquals(1, results.size());
        assertEquals(restaurant.getId(), results.get(0).getId());
    }

    @Test
    public void testGetFilteredRestaurantsByDishProvidingNonExistentNameShouldReturnEmptyList() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setDescription("Test Description");
        entityManager.persist(restaurant);

        Dish dish = new Dish();
        dish.setDishName("Existing Dish");
        dish.setPrice(BigDecimal.valueOf(10.99));
        dish.setRestaurant(restaurant);
        entityManager.persist(dish);

        // when
        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(null, null, null, List.of("Non-Existent Dish"));
        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());
        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        // then
        assertTrue(results.isEmpty());
    }
}
