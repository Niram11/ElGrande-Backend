package com.codecool.gastro.service;

import com.codecool.gastro.controller.dto.ingredientDto.NewIngredientDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.EntityNotFoundException;
import com.codecool.gastro.service.mapper.IngredientMapper;
import com.codecool.gastro.service.mapper.RestaurantMenuMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RestaurantMenuMapper restaurantMenuMapper;

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository, IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, RestaurantMenuMapper restaurantMenuMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.restaurantMenuMapper = restaurantMenuMapper;
    }

    public RestaurantMenuDto saveNewRestaurantMenu(NewRestaurantMenuDto restaurantMenuDto) {
        RestaurantMenu newRestaurantMenu = restaurantMenuRepository
                .save(restaurantMenuMapper.dtoToRestaurantMenu(restaurantMenuDto));
        return restaurantMenuMapper.getMenuDto(newRestaurantMenu);
    }

    public List<RestaurantMenuDto> getAllMenus() {
        return restaurantMenuRepository.findAll().stream()
                .map(restaurantMenuMapper::getMenuDto)
                .toList();
    }

    public void assignIngredientToMenu(UUID restaurantMenuId, Set<NewIngredientDto> ingredients) {
        RestaurantMenu menu = restaurantMenuRepository.findOneById(restaurantMenuId)
                .orElseThrow(() -> new EntityNotFoundException(restaurantMenuId, RestaurantMenu.class));


        addIngredientsToMenu(ingredients, menu);
        restaurantMenuRepository.save(menu);
    }

    public void deleteMenu(UUID id) {
        RestaurantMenu deletedMenu = restaurantMenuMapper.dtoToRestaurantMenu(id);
        Set<Ingredient> emptySet = new HashSet<>();
        deletedMenu.setIngredients(emptySet);
        restaurantMenuRepository.delete(deletedMenu);
    }

    public RestaurantMenuDto getRestaurantMenuById(UUID id) {
        return restaurantMenuRepository.findById(id)
                .map(restaurantMenuMapper::getMenuDto)
                .orElseThrow(() -> new EntityNotFoundException(id, RestaurantMenu.class));
    }

    private void addIngredientsToMenu(Set<NewIngredientDto> ingredients, RestaurantMenu menu) {
        for (NewIngredientDto ingredient : ingredients) {

            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(ingredient.name());
            Ingredient mappedIngredient = ingredientMapper.dtoToIngredient(ingredient);

            if (ingredientOptional.isEmpty()) {

                ingredientRepository.save(mappedIngredient);
                menu.assignIngredient(mappedIngredient);

            } else if (!menu.getIngredients().contains(ingredient)) {

                menu.assignIngredient(mappedIngredient);
            }
        }
    }
}
