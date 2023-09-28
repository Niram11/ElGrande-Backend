package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository repository;

    private UUID ingredientId;

    @BeforeEach
    void setUp() {
        ingredientId = UUID.fromString("7f357c2a-a0ce-4a8f-9c07-ad34e3be7497");
    }

    @Test
    void testFindAll_ShouldReturnList_WhenCalled() {
        // when
        List<Ingredient> list = repository.findAll();

        // then
        assertEquals(list.size(), 2);
    }

    @Test
    void testFindById_ShouldReturnIngredient_WhenExist() {
        // when
        Optional<Ingredient> ingredient = repository.findById(ingredientId);

        // then
        assertTrue(ingredient.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoIngredient() {
        // when
        Optional<Ingredient> ingredient = repository.findById(UUID.randomUUID());

        // then
        assertTrue(ingredient.isEmpty());
    }

    @Test
    void testFindByName_ShouldReturnIngredient_WhenExist() {
        // when
        Optional<Ingredient> ingredient = repository.findByName("Tomato");

        // then
        assertTrue(ingredient.isPresent());
    }

    @Test
    void testFindByName_ShouldReturnEmptyOptional_WhenNoIngredient() {
        // when
        Optional<Ingredient> ingredient = repository.findByName("");

        // then
        assertTrue(ingredient.isEmpty());
    }
}