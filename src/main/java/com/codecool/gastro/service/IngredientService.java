package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    public IngredientDto getIngredientBy(UUID id) {
        return ingredientRepository.findById(id)
                .map(ingredientMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, RestaurantMenu.class));
    }

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    public IngredientDto saveNewIngredient(NewIngredientDto ingredientDto) {
        Ingredient newIngredient = ingredientMapper.dtoToIngredient(ingredientDto);
        return ingredientMapper.toDto(ingredientRepository.save(parseToLowerCase(newIngredient)));
    }

    public IngredientDto updateIngredient(UUID id, NewIngredientDto newIngredientDto ){
        Ingredient updatedIngredient = ingredientMapper.dtoToIngredient(id,newIngredientDto);
        return ingredientMapper.toDto(ingredientRepository.save(parseToLowerCase(updatedIngredient)));
    }

    public void deleteIngredient(UUID id) {
        Ingredient deletedIngredient = ingredientMapper.dtoToIngredient(id);
        ingredientRepository.delete(deletedIngredient);
    }

    private Ingredient parseToLowerCase(Ingredient ingredient) {
        ingredient.setName(ingredient.getName().toLowerCase());
        return ingredient;
    }
}
