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
    IngredientMapper mapper = Mappers.getMapper(IngredientMapper.class);

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
    void testDtoToIngredient_ShouldReturnIngredient_WhenProvidingId() {
        // when
        Ingredient testedIngredient = mapper.dtoToIngredient(ingredientId);

        // then
        assertEquals(testedIngredient.getId(), ingredientId);
    }

    @Test
    void testDtoToIngredient_ShouldReturnIngredient_WhenProvidingDto() {
        // when
        Ingredient testedIngredient = mapper.dtoToIngredient(newIngredientDto);

        // then
        assertEquals(testedIngredient.getName(), newIngredientDto.name());
    }
}