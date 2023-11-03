package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RestaurantPredicateByRestaurantCategoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetFilteredRestaurantsByCategoryProvidingProperCategoryShouldReturnRestaurant() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setDescription("Test Description");
        entityManager.persist(restaurant);

        RestaurantCategory category = new RestaurantCategory();
        category.setCategory("Test Category");
        category.getRestaurants().add(restaurant);
        entityManager.persist(category);

        // when
        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(null, List.of("Test Category"), null, null);
        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());
        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        // then
        assertEquals(1, results.size());
        assertEquals(restaurant.getId(), results.get(0).getId());
    }

    @Test
    public void testGetFilteredRestaurantsByCategoryProvidingNonExistentCategoryShouldReturnEmptyList() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setDescription("Test Description");
        entityManager.persist(restaurant);

        RestaurantCategory category = new RestaurantCategory();
        category.setCategory("Existing Category");
        category.getRestaurants().add(restaurant);
        entityManager.persist(category);

        // when
        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(null, List.of("Non-Existent Category"), null, null);
        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());
        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        // then
        assertTrue(results.isEmpty());
    }
}
