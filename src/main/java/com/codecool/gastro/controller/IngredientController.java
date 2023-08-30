package com.codecool.gastro.controller;

import com.codecool.gastro.controller.dto.ingredientDto.IngredientDto;
import com.codecool.gastro.controller.dto.ingredientDto.NewIngredientDto;
import com.codecool.gastro.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public IngredientDto createIngredient(@Valid @RequestBody NewIngredientDto newIngredientDto){
        return ingredientService.saveNewIngredient(newIngredientDto);
    }
}
