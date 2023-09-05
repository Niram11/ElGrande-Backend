package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.EntityNotFoundException;
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

    public IngredientDto saveNewIngredient(NewIngredientDto ingredientDto) {
        Ingredient newIngredient = ingredientMapper.dtoToIngredient(ingredientDto);
        newIngredient.setName(newIngredient.getName().toLowerCase());
        return ingredientMapper.getIngredientDto(ingredientRepository.save(newIngredient));
    }

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::getIngredientDto)
                .toList();
    }

    public IngredientDto getIngredientById(UUID id) {
        return ingredientRepository.findById(id)
                .map(ingredientMapper::getIngredientDto)
                .orElseThrow(() -> new EntityNotFoundException(id, RestaurantMenu.class));
    }

    public void deleteIngredient(UUID id) {
        Ingredient deletedIngredient = ingredientMapper.dtoToIngredient(id);
        ingredientRepository.delete(deletedIngredient);
    }
}
