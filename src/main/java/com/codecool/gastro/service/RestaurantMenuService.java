package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.dto.menucategory.NewMenuCategoryDto;
import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.MenuCategoryRepository;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.MenuCategory;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.IngredientMapper;
import com.codecool.gastro.service.mapper.MenuCategoryMapper;
import com.codecool.gastro.service.mapper.RestaurantMenuMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantMenuMapper restaurantMenuMapper;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuCategoryMapper menuCategoryMapper;

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository,
                                 RestaurantMenuMapper restaurantMenuMapper, IngredientMapper ingredientMapper,
                                 IngredientRepository ingredientRepository, MenuCategoryRepository menuCategoryRepository,
                                 MenuCategoryMapper menuCategoryMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.menuCategoryRepository = menuCategoryRepository;
        this.menuCategoryMapper = menuCategoryMapper;
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

    public void assignMenuCategoryToMenu(UUID restaurantMenuId, Set<NewMenuCategoryDto> categories) {
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
    private void addMenuCategoriesToMenu(Set<NewMenuCategoryDto> categories, RestaurantMenu menu) {
        for (NewMenuCategoryDto category : categories) {

            Optional<MenuCategory> menuCategory = menuCategoryRepository.findBy(category.category());
            MenuCategory mappedMenuCategory = menuCategoryMapper.dtoToMenuCategory(category);

            if (menuCategory.isEmpty()) {

                menuCategoryRepository.save(mappedMenuCategory);
                menu.assignCategories(mappedMenuCategory);

            } else if (!menu.getCategories().contains(menuCategory.get())) {

                menu.assignCategories(menuCategory.get());
            }
        }
    }




}
