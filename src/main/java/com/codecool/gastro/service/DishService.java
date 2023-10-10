package com.codecool.gastro.service;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;
    private final DishCategoryService dishCategoryService;
    private final DishCategoryRepository dishCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository,
                       DishMapper dishMapper, IngredientService ingredientService,
                       IngredientRepository ingredientRepository, DishCategoryService dishCategoryService,
                       DishCategoryRepository dishCategoryRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.dishCategoryService = dishCategoryService;
        this.dishCategoryRepository = dishCategoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<DishDto> getDishesByRestaurantId(UUID id) {
        return dishRepository.findByRestaurantId(id)
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
        restaurantRepository.findById(newDishDto.restaurantId())
                .orElseThrow(() -> new ObjectNotFoundException(newDishDto.restaurantId(), Restaurant.class));

        Dish savedDish = dishRepository.save(dishMapper.dtoToDish(newDishDto));
        return dishMapper.toDto(savedDish);
    }

    public DishDto updateDish(UUID id, EditDishDto editDishDto) {
        Dish updatedDish = dishRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Dish.class));

        dishMapper.updatedDishFromDto(editDishDto, updatedDish);
        return dishMapper.toDto(dishRepository.save(updatedDish));
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
        ingredients.forEach(ingredient -> {
            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(ingredient.name().toLowerCase());

            ingredientOptional.ifPresentOrElse(dish::assignIngredient,
                    () -> {
                        IngredientDto savedIngredient = ingredientService.saveNewIngredient(ingredient);
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setName(savedIngredient.name());
                        newIngredient.setId(savedIngredient.id());
                        dish.assignIngredient(newIngredient);
                    });
        });
    }

    private void addDishCategoryToDish(Set<NewDishCategoryDto> categories, Dish dish) {
        categories.forEach(category -> {
            Optional<DishCategory> dishCategoryOptional = dishCategoryRepository.findByCategory(category.category());

            dishCategoryOptional.ifPresentOrElse(dish::assignCategories,
                    () -> {
                        DishCategoryDto savedDishCategory = dishCategoryService.saveDishCategory(category);
                        DishCategory newDishCategory = new DishCategory();
                        newDishCategory.setCategory(savedDishCategory.category());
                        newDishCategory.setId(savedDishCategory.id());
                        dish.assignCategories(newDishCategory);
                    });
        });
    }

}
