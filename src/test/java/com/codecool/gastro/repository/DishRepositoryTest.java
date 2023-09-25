package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DishRepositoryTest {

    @Autowired
    private DishRepository repository;


    private UUID dishId;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        dishId = UUID.fromString("f454d079-4179-41b5-858e-bdc5ad953256");
        restaurantId = UUID.fromString("c728af54-0d03-4af1-a68e-6364db2370ee");
    }

    @Test
    void testFindAll_ShouldReturnListOfDishes_WhenCalled() {
        // when
        List<Dish> list = repository.findAll();

        // then
        assertEquals(list.size(), 4);
    }

    @Test
    void testFindById_ShouldReturnDish_WhenExist() {
        // when
        Optional<Dish> dish = repository.findById(dishId);

        // then
        assertTrue(dish.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoDish() {
        // when
        Optional<Dish> dish = repository.findById(UUID.randomUUID());

        // then
        assertTrue(dish.isEmpty());
    }

    @Test
    void testFindByRestaurant_ShouldReturnListOfDishes_WhenCalled() {
        // when
        List<Dish> list = repository.findByRestaurant(restaurantId);

        // then
        assertEquals(list.size(), 2);
    }
}