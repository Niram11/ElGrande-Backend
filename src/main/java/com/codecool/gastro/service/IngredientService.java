package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    public IngredientDto saveNewIngredient(NewIngredientDto newIngredientDto) {
        Ingredient savedIngredient = ingredientMapper.dtoToIngredient(newIngredientDto);
        parseToLowerCase(savedIngredient);
        return ingredientMapper.toDto(ingredientRepository.save(savedIngredient));
    }

    public void deleteIngredient(UUID id) {
        ingredientRepository.delete(ingredientMapper.dtoToIngredient(id));
    }

    private void parseToLowerCase(Ingredient ingredient) {
        ingredient.setName(ingredient.getName().toLowerCase());
    }
}
