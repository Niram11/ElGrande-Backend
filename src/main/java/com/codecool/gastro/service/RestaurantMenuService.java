package com.codecool.gastro.service;

import com.codecool.gastro.controller.dto.restaurantMenuDto.NewRestaurantMenuDto;
import com.codecool.gastro.controller.dto.restaurantMenuDto.RestaurantMenuDto;
import com.codecool.gastro.service.mapper.RestaurantMenuMapper;
import com.codecool.gastro.repository.RestaurantMenuRepository;
import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .save(restaurantMenuMapper.dtoToRestaurantMenu(restaurantMenuDto));
        return restaurantMenuMapper.getMenuDto(newRestaurantMenu);
    }

    public List<RestaurantMenuDto> getAllMenus() {
        return restaurantMenuRepository.findAll().stream()
                .map(restaurantMenuMapper:: getMenuDto)
                .toList();
    }
}
