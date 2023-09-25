package com.codecool.gastro.service;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishCategoryMapper;
import com.codecool.gastro.service.mapper.DishMapper;
import com.codecool.gastro.service.mapper.IngredientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final DishCategoryRepository dishCategoryRepository;
    private final DishCategoryMapper dishCategoryMapper;

    public DishService(DishRepository dishRepository,
                       DishMapper dishMapper, IngredientMapper ingredientMapper,
                       IngredientRepository ingredientRepository, DishCategoryRepository dishCategoryRepository,
                       DishCategoryMapper dishCategoryMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.dishCategoryRepository = dishCategoryRepository;
        this.dishCategoryMapper = dishCategoryMapper;
    }

    public List<DishDto> getAllDishes() {
        return dishRepository.findAll()
                .stream()
                .map(dishMapper::toDto)
                .toList();
    }

    public List<DishDto> getDishesByRestaurant(UUID id) {
        return dishRepository.findByRestaurant(id)
                .stream()
                .map(dishMapper::toDto)
                .toList();
    }

    public DishDto getDishById(UUID id) {
        return dishRepository.findById(id)
                .map(dishMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Dish.class));
    }

    public DishDto saveNewDish(NewDishDto newDishDto) {
        Dish savedDish = dishRepository.save(dishMapper.dtoToDish(newDishDto));
        return dishMapper.toDto(savedDish);
    }

    public DishDto updateDish(UUID id, NewDishDto newDishDto) {
        Dish updatedDish = dishRepository.save(dishMapper.dtoToDish(id, newDishDto));
        return dishMapper.toDto(updatedDish);
    }

    public void deleteDish(UUID id) {
        dishRepository.delete(dishMapper.dtoToDish(id));
    }

    public void assignIngredientToDish(UUID dishId, Set<NewIngredientDto> ingredients) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(dishId, Dish.class));


        addIngredientsToDish(ingredients, dish);
        dishRepository.save(dish);
    }

    @Transactional
    public void assignDishCategoryToDish(UUID dishId, Set<NewDishCategoryDto> categories) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(dishId, Dish.class));


        addDishCategoryToDish(categories, dish);
        dishRepository.save(dish);
    }

    private void addIngredientsToDish(Set<NewIngredientDto> ingredients, Dish dish) {
        for (NewIngredientDto ingredient : ingredients) {

            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(ingredient.name());

            if (ingredientOptional.isEmpty()) {
                Ingredient mappedIngredient = ingredientMapper.dtoToIngredient(ingredient);
                mappedIngredient.setName(mappedIngredient.getName().toLowerCase());
                ingredientRepository.save(mappedIngredient);
                dish.assignIngredient(mappedIngredient);

            } else if (!dish.getIngredients().contains(ingredientOptional.get())) {

                dish.assignIngredient(ingredientOptional.get());
            }
        }
    }

    private void addDishCategoryToDish(Set<NewDishCategoryDto> categories, Dish dish) {
        for (NewDishCategoryDto category : categories) {

            Optional<DishCategory> dishCategory = dishCategoryRepository.findByCategory(category.category());

            if (dishCategory.isEmpty()) {
                DishCategory mappedDishCategory = dishCategoryMapper.dtoToDishCategory(category);
                mappedDishCategory.setCategory(mappedDishCategory.getCategory().toLowerCase());
                dishCategoryRepository.save(mappedDishCategory);
                dish.assignCategories(mappedDishCategory);

            } else if (!dish.getCategories().contains(dishCategory.get())) {

                dish.assignCategories(dishCategory.get());
            }
        }
    }

}
