package com.codecool.gastro.service;

import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.dto.restaurantmenu.NewRestaurantMenuDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
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

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository,
                                 IngredientRepository ingredientRepository, IngredientMapper ingredientMapper,
                                 RestaurantMenuMapper restaurantMenuMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.restaurantMenuMapper = restaurantMenuMapper;
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
        RestaurantMenu menu = restaurantMenuRepository.findOneById(restaurantMenuId)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantMenuId, RestaurantMenu.class));


        addIngredientsToMenu(ingredients, menu);
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


}
