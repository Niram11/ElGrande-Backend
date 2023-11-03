package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RestaurantPredicateByNameTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetFilteredRestaurantsByNameProvidingProperNameShouldReturnRestaurant() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("name1");
        restaurant1.setDescription("description1");
        restaurant1.setWebsite("website.pl");
        restaurant1.setContactNumber(123456789);
        restaurant1.setContactEmail("email@xd.pl");
        entityManager.persist(restaurant1);

        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());

        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(List.of("name1"), null, null, null);

        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        assertEquals(1, results.size());
        assertEquals(restaurant1, results.get(0));
    }

    @Test
    public void testGetFilteredRestaurantsByNameProvidingWrongNameShouldReturnEmptyList() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("name1");
        restaurant1.setDescription("description1");
        restaurant1.setWebsite("website.pl");
        restaurant1.setContactNumber(123456789);
        restaurant1.setContactEmail("email@xd.pl");
        entityManager.persist(restaurant1);

        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager.getEntityManager());

        FilteredRestaurantsCriteria criteria = new FilteredRestaurantsCriteria(List.of("nonExistentName"), null, null, null);

        List<Restaurant> results = specification.getFilteredRestaurants(criteria);

        assertEquals(0, results.size());
    }
}
