package com.codecool.gastro.service;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.mapper.DishMapper;
import com.codecool.gastro.service.validation.DishValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final DishValidation validation;
    private final RestaurantValidation restaurantValidation;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;
    private final DishCategoryService dishCategoryService;
    private final DishCategoryRepository dishCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository,
                       DishMapper dishMapper, DishValidation validation, RestaurantValidation restaurantValidation, IngredientService ingredientService,
                       IngredientRepository ingredientRepository, DishCategoryService dishCategoryService,
                       DishCategoryRepository dishCategoryRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.validation = validation;
        this.restaurantValidation = restaurantValidation;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.dishCategoryService = dishCategoryService;
        this.dishCategoryRepository = dishCategoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<DishDto> getDishesByRestaurantId(UUID restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(dishMapper::toDto)
                .toList();
    }

    public DishDto getDishById(UUID id) {
        return dishMapper.toDto(validation.validateEntityById(id));
    }

    public DishDto saveNewDish(NewDishDto newDishDto) {
        restaurantValidation.validateEntityById(newDishDto.restaurantId());
        return dishMapper.toDto(dishRepository.save(dishMapper.dtoToDish(newDishDto)));
    }

    public DishDto updateDish(UUID id, EditDishDto editDishDto) {
        Dish updatedDish = validation.validateEntityById(id);
        dishMapper.updatedDishFromDto(editDishDto, updatedDish);
        return dishMapper.toDto(dishRepository.save(updatedDish));
    }

    public void deleteDish(UUID id) {
        dishRepository.delete(dishMapper.dtoToDish(id));
    }

    @Transactional
    public void assignIngredientToDish(UUID dishId, Set<NewIngredientDto> ingredients) {
        Dish dish = validation.validateEntityById(dishId);
        addIngredientsToDish(ingredients, dish);
        dishRepository.save(dish);
    }

    @Transactional
    public void assignDishCategoryToDish(UUID dishId, Set<NewDishCategoryDto> categories) {
        Dish dish = validation.validateEntityById(dishId);
        addDishCategoryToDish(categories, dish);
        dishRepository.save(dish);
    }

    private void addIngredientsToDish(Set<NewIngredientDto> ingredients, Dish dish) {
        ingredients.forEach(ingredient -> ingredientRepository.findByName(ingredient.name().toLowerCase())
                .ifPresentOrElse(dish::assignIngredient, () -> {
                    IngredientDto savedIngredient = ingredientService.saveNewIngredient(ingredient);
                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setName(savedIngredient.name());
                    newIngredient.setId(savedIngredient.id());
                    dish.assignIngredient(newIngredient);
                })
        );
    }

    private void addDishCategoryToDish(Set<NewDishCategoryDto> categories, Dish dish) {
        categories.forEach(category -> dishCategoryRepository.findByCategory(category.category().toLowerCase())
                .ifPresentOrElse(dish::assignCategories, () -> {
                    DishCategoryDto savedDishCategory = dishCategoryService.saveDishCategory(category);
                    DishCategory newDishCategory = new DishCategory();
                    newDishCategory.setCategory(savedDishCategory.category());
                    newDishCategory.setId(savedDishCategory.id());
                    dish.assignCategories(newDishCategory);
                })
        );
    }

}
