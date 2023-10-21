package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDto toDto(Ingredient ingredient);

    @Mapping(target = "id", ignore = true)
    Ingredient dtoToIngredient(NewIngredientDto ingredientDto);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", source = "id")
    Ingredient dtoToIngredient(UUID id);

}
