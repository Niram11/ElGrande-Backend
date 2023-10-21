package com.codecool.gastro.service;


import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.repository.RestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantCategoryMapper restaurantCategoryMapper;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository,
                                     RestaurantCategoryMapper restaurantCategoryMapper) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.restaurantCategoryMapper = restaurantCategoryMapper;
    }

    public List<RestaurantCategoryDto> getRestaurantCategories() {
        return restaurantCategoryRepository.findAll()
                .stream()
                .map(restaurantCategoryMapper::toDto)
                .toList();
    }

    public RestaurantCategoryDto saveRestaurantCategory(NewRestaurantCategoryDto newRestaurantCategoryDTO) {
        RestaurantCategory savedRestaurantCategory = restaurantCategoryRepository
                .save(restaurantCategoryMapper.dtoToRestaurantCategory(newRestaurantCategoryDTO));
        return restaurantCategoryMapper.toDto(savedRestaurantCategory);
    }

    public void deleteRestaurantCategory(UUID id) {
        restaurantCategoryRepository.delete(restaurantCategoryMapper.dtoToRestaurantCategory(id));
    }
}