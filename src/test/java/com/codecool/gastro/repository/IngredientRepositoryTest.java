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

    @Test
    void testFindAll_ShouldReturnList_WhenCalled() {
        // when
        List<Ingredient> list = repository.findAll();

        // then
        assertEquals(list.size(), 2);
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