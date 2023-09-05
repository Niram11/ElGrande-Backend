package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDto getIngredientDto(Ingredient ingredient);

    Ingredient dtoToIngredient(NewIngredientDto ingredientDto);

    @Mapping(source = "id", target = "id")
    Ingredient dtoToIngredient(UUID id);
}
