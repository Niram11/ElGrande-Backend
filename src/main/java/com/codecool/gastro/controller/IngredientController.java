package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getAllIngredients());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IngredientDto> createIngredient(@Valid @RequestBody NewIngredientDto newIngredientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveNewIngredient(newIngredientDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngredientDto> deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
