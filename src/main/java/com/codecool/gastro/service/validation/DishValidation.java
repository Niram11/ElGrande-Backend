package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.UUID;

public class DishValidation implements Validation<UUID> {
    private final DishRepository dishRepository;

    public DishValidation(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    @Override
    public void validateUpdate(UUID id) {
        dishRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Dish.class));
    }

    public void validateAssignIngredientToDish(UUID id) {
        dishRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Dish.class));
    }
}
