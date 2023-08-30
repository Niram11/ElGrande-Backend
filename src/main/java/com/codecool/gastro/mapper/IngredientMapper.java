package com.codecool.gastro.mapper;

import com.codecool.gastro.controller.dto.ingredientDto.IngredientDto;
import com.codecool.gastro.controller.dto.ingredientDto.NewIngredientDto;
import com.codecool.gastro.repository.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDto mapIngredientToDto(Ingredient entity) {
        return new IngredientDto(
                entity.getId(),
                entity.getName()
        );
    }

    public Ingredient mapDtoToIngredient(NewIngredientDto dto){
        return new Ingredient(
                dto.name()
        );
    }
}
