package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientMapperTest {

    private IngredientMapper mapper = Mappers.getMapper(IngredientMapper.class);

    private UUID ingredientId;
    private Ingredient ingredient;
    private NewIngredientDto newIngredientDto;

    @BeforeEach
    void setUp() {
        ingredientId = UUID.randomUUID();

        ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setName("Name");

        newIngredientDto = new NewIngredientDto(
                "Tomato"
        );
    }

    @Test
    void testToDto_ShouldReturnIngredientDto_WhenCalled() {
        // when
        IngredientDto testedIngredientDto = mapper.toDto(ingredient);

        // then
        assertEquals(ingredient.getId(), testedIngredientDto.id());
        assertEquals(ingredient.getName(), testedIngredientDto.name());
    }

    @Test
    void testDtoToIngredient_ShouldReturnIngredient_WhenCalled() {
        // when
        Ingredient ingredientOne = mapper.dtoToIngredient(ingredientId);
        Ingredient ingredientTwo = mapper.dtoToIngredient(ingredientId, newIngredientDto);
        Ingredient ingredientThree = mapper.dtoToIngredient(newIngredientDto);

        // then
        assertEquals(ingredientOne.getId(), ingredientId);

        assertEquals(ingredientTwo.getId(), ingredientId);
        assertEquals(ingredientTwo.getName(), newIngredientDto.name());

        assertEquals(ingredientThree.getName(), newIngredientDto.name());
    }
}