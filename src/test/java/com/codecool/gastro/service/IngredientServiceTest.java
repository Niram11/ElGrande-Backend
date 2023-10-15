package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.IngredientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @InjectMocks
    private IngredientService service;
    @Mock
    private IngredientMapper mapper;
    @Mock
    private IngredientRepository repository;

    private UUID ingredientId;
    private Ingredient ingredient;
    private IngredientDto ingredientDto;
    private NewIngredientDto newIngredientDto;

    @BeforeEach
    void setUp() {
        ingredientId = UUID.randomUUID();

        ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setName("Tomato");

        ingredientDto = new IngredientDto(
                ingredientId,
                "Tomato"
        );

        newIngredientDto = new NewIngredientDto(
                "Tomato"
        );
    }

    @Test
    void testGetAllIngredients_ShouldReturnList_WhenCalled() {
        // when
        when(repository.findAll()).thenReturn(List.of());
        List<IngredientDto> list = service.getAllIngredients();

        // then
        assertEquals(list.size(), 0);
    }

    @Captor
    ArgumentCaptor<Ingredient> captor;

    @Test
    void testSaveNewIngredient_ShouldParseNameToLowerCaseBeforeSaving_WhenCalled() {
        // when
        when(mapper.dtoToIngredient(newIngredientDto)).thenReturn(ingredient);
        when(repository.save(ingredient)).thenReturn(ingredient);
        when(mapper.toDto(ingredient)).thenReturn(ingredientDto);
        service.saveNewIngredient(newIngredientDto);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getName(), "tomato");
    }
}