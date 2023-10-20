package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RestaurantCategoryRepositoryTest {

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Test
    void testFindAll_ShouldReturnAllCategories_WhenExist() {
        // Given
        RestaurantCategory category1 = new RestaurantCategory();
        category1.setCategory("Category 1");
        restaurantCategoryRepository.save(category1);

        RestaurantCategory category2 = new RestaurantCategory();
        category2.setCategory("Category 2");
        restaurantCategoryRepository.save(category2);

        // When
        List<RestaurantCategory> categories = restaurantCategoryRepository.findAll();

        // Then
        assertThat(categories).isNotEmpty().hasSize(2).contains(category1, category2);
    }
}