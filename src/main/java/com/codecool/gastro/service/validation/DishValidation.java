package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DishValidation implements Validation<Dish> {
    private final DishRepository dishRepository;

    public DishValidation(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    @Override
    public Dish validateEntityById(UUID id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Dish.class));
    }
}
