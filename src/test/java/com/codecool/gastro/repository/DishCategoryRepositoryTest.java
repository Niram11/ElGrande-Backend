package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
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
    DishCategoryRepository repository;

    @Test
    void testFindAll_ShouldReturnList_WhenCalled() {
        // when
        List<DishCategory> list = repository.findAll();

        // then
        assertEquals(list.size(), 2);
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