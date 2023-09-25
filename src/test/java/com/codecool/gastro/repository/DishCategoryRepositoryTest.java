package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.DishCategory;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DishCategoryRepositoryTest {

    @Autowired
    private DishCategoryRepository repository;

    private UUID dishCategoryId;

    @BeforeEach
    void setUp() {
        dishCategoryId = UUID.fromString("0188a205-ed29-405e-9245-4b714a0db157");
    }

    @Test
    void testFindAll_ShouldReturnListOfDishCategories_WhenCalled() {
        // when
        List<DishCategory> list = repository.findAll();

        // then
        assertEquals(list.size(), 2);
    }

    @Test
    void testFindById_ShouldReturnDishCategory_WhenExist() {
        // when
        Optional<DishCategory> dishCategory = repository.findById(dishCategoryId);

        // then
        assertTrue(dishCategory.isPresent());
    }
    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoDishCategory() {
        // when
        Optional<DishCategory> dishCategory = repository.findById(UUID.randomUUID());

        // then
        assertTrue(dishCategory.isEmpty());
    }

    @Test
    void testFindByCategory_ShouldReturnDishCategory_WhenExist() {
        // when
        Optional<DishCategory> dishCategory = repository.findByCategory("Meat");

        // then
        assertTrue(dishCategory.isPresent());
    }

    @Test
    void testFindByCategory_ShouldReturnEmptyOptional_WhenNoDishCategory() {
        // when
        Optional<DishCategory> dishCategory = repository.findByCategory("");

        // then
        assertTrue(dishCategory.isEmpty());
    }

}