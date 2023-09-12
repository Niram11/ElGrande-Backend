package com.codecool.gastro.service;

import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishCategoryMapper;
import com.codecool.gastro.service.mapper.IngredientMapper;
import com.codecool.gastro.service.mapper.RestaurantMenuMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantMenuMapper restaurantMenuMapper;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final DishCategoryRepository dishCategoryRepository;
    private final DishCategoryMapper dishCategoryMapper;

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository,
                                 RestaurantMenuMapper restaurantMenuMapper, IngredientMapper ingredientMapper,
                                 IngredientRepository ingredientRepository, DishCategoryRepository menuCategoryRepository,
                                 DishCategoryMapper dishCategoryMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.dishCategoryRepository = menuCategoryRepository;
        this.dishCategoryMapper = dishCategoryMapper;
    }

    public List<RestaurantMenuDto> getAllRestaurantMenus() {
        return restaurantMenuRepository.findAll()
                .stream()
                .map(restaurantMenuMapper::toDto)
                .toList();
    }

    public RestaurantMenuDto getRestaurantMenuBy(UUID id) { 
        return restaurantMenuRepository.findById(id)
                .map(restaurantMenuMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, RestaurantMenu.class));
    }

    public RestaurantMenuDto saveNewRestaurantMenu(NewRestaurantMenuDto newRestaurantMenuDto) {
        RestaurantMenu savedRestaurantMenu = restaurantMenuRepository
                .save(restaurantMenuMapper.dtoToRestaurantMenu(newRestaurantMenuDto));
        return restaurantMenuMapper.toDto(savedRestaurantMenu);
    }

    public RestaurantMenuDto updateRestaurantMenu(UUID id, NewRestaurantMenuDto newRestaurantMenuDto) {
        RestaurantMenu updatedRestaurantMenu = restaurantMenuRepository
                .save(restaurantMenuMapper.dtoToRestaurantMenu(newRestaurantMenuDto, id));
        return restaurantMenuMapper.toDto(updatedRestaurantMenu);
    }

    public void deleteRestaurantMenu(UUID id) {
        restaurantMenuRepository.delete(restaurantMenuMapper.dtoToRestaurantMenu(id));
    }

    public void assignIngredientToMenu(UUID restaurantMenuId, Set<NewIngredientDto> ingredients) {
        RestaurantMenu menu = restaurantMenuRepository.findById(restaurantMenuId)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantMenuId, RestaurantMenu.class));


        addIngredientsToMenu(ingredients, menu);
        restaurantMenuRepository.save(menu);
    }

    public void assignMenuCategoryToMenu(UUID restaurantMenuId, Set<NewDishCategoryDto> categories) {
        RestaurantMenu menu = restaurantMenuRepository.findById(restaurantMenuId)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantMenuId, RestaurantMenu.class));


        addMenuCategoriesToMenu(categories, menu);
        restaurantMenuRepository.save(menu);
    }

    private void addIngredientsToMenu(Set<NewIngredientDto> ingredients, RestaurantMenu menu) {
        for (NewIngredientDto ingredient : ingredients) {

            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(ingredient.name());
            Ingredient mappedIngredient = ingredientMapper.dtoToIngredient(ingredient);

            if (ingredientOptional.isEmpty()) {

                ingredientRepository.save(mappedIngredient);
                menu.assignIngredient(mappedIngredient);

            } else if (!menu.getIngredients().contains(ingredientOptional.get())) {

                menu.assignIngredient(ingredientOptional.get());
            }
        }
    }
    private void addMenuCategoriesToMenu(Set<NewDishCategoryDto> categories, RestaurantMenu menu) {
        for (NewDishCategoryDto category : categories) {

            Optional<DishCategory> menuCategory = dishCategoryRepository.findBy(category.category());
            DishCategory mappedMenuCategory = dishCategoryMapper.dtoToDishCategory(category);

            if (menuCategory.isEmpty()) {

                dishCategoryRepository.save(mappedMenuCategory);
                menu.assignCategories(mappedMenuCategory);

            } else if (!menu.getCategories().contains(menuCategory.get())) {

                menu.assignCategories(menuCategory.get());
            }
        }
    }




}
