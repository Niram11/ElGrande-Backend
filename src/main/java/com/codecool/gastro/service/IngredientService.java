package com.codecool.gastro.service;

import com.codecool.gastro.controller.dto.ingredientDto.IngredientDto;
import com.codecool.gastro.controller.dto.ingredientDto.NewIngredientDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.service.mapper.IngredientMapper;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    public IngredientDto saveNewIngredient(NewIngredientDto ingredientDto) {
        Ingredient newIngredient = ingredientRepository
                .save(ingredientMapper.dtoToIngredient(ingredientDto));
        return ingredientMapper.getIngredientDto(newIngredient);
    }

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::getIngredientDto)
                .toList();
    }
}
