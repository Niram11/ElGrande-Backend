package com.codecool.gastro.service;

import com.codecool.gastro.controller.dto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.RestaurantMenuDto;
import com.codecool.gastro.mapper.RestaurantMenuMapper;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.stereotype.Service;

@Service
public class RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantMenuMapper restaurantMenuMapper;

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository, RestaurantMenuMapper restaurantMenuMapper) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
    }

    public RestaurantMenuDto saveNewRestaurantMenu(NewRestaurantMenuDto restaurantMenuDto){
        RestaurantMenu newRestaurantMenu = restaurantMenuRepository
                .save(restaurantMenuMapper.mapNewRestaurantMenuDtoToEntity(restaurantMenuDto));
        return restaurantMenuMapper.mapRestaurantEntityToDto(newRestaurantMenu);
    }
}
