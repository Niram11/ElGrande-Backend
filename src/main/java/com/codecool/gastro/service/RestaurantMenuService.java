package com.codecool.gastro.service;

import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.mapper.RestaurantMenuMapper;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final IngredientRepository ingredientRepository;

    private final RestaurantMenuMapper restaurantMenuMapper;
    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository, IngredientRepository ingredientRepository, RestaurantMenuMapper restaurantMenuMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.ingredientRepository = ingredientRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
    }

    public RestaurantMenuDto saveNewRestaurantMenu(NewRestaurantMenuDto restaurantMenuDto){
        RestaurantMenu newRestaurantMenu = restaurantMenuRepository
                .save(restaurantMenuMapper.dtoToRestaurantMenu(restaurantMenuDto));
        return restaurantMenuMapper.getMenuDto(newRestaurantMenu);
    }

    public List<RestaurantMenuDto> getAllMenus() {
        return restaurantMenuRepository.findAll().stream()
                .map(restaurantMenuMapper:: getMenuDto)
                .toList();
    }

    public void assignIngredientToMenu(UUID restaurantMenuId, UUID ingredientId) {
        RestaurantMenu menu = restaurantMenuRepository.findOneById(restaurantMenuId)
                .orElseThrow(() -> new RuntimeException("No such restaurant menu"));

        Ingredient ingredient = ingredientRepository.findOneById(ingredientId)
                .orElseThrow(() -> new RuntimeException("No such ingredient"));

        menu.assignIngredient(ingredient);
        ingredient.addRestaurantMenu(menu);

        restaurantMenuRepository.save(menu);
    }
}
