package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientDto> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientDto getIngredient(@PathVariable UUID id) {
        return ingredientService.getIngredientBy(id);
    }

    @PostMapping
    public IngredientDto createIngredient(@Valid @RequestBody NewIngredientDto newIngredientDto) {
        return ingredientService.saveNewIngredient(newIngredientDto);
    }

    @PutMapping("/{id}")
    public IngredientDto updateIngredient(@PathVariable UUID id, @Valid @RequestBody NewIngredientDto newIngredientDto){
        return ingredientService.updateIngredient(id, newIngredientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteIngredient(id);
    }
}
