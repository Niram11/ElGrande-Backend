package com.codecool.gastro.service;


import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.repository.RestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import com.codecool.gastro.service.mapper.RestaurantCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantCategoryService {

    private RestaurantCategoryRepository restaurantCategoryRepository;
    private RestaurantCategoryMapper restaurantCategoryMapper;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository
            , RestaurantCategoryMapper restaurantCategoryMapper) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.restaurantCategoryMapper = restaurantCategoryMapper;
    }

    public List<RestaurantCategoryDto> getRestaurantCategories() {
        return restaurantCategoryRepository.findAll().stream().map(restaurantCategoryMapper::restaurantCategoryToDto)
                .toList();
    }

    public RestaurantCategoryDto getRestaurantCategoryByUUID(UUID id) {
        return restaurantCategoryRepository.findById(id).map(restaurantCategoryMapper::restaurantCategoryToDto)
                .orElseThrow(() -> new RuntimeException());
    }

    public RestaurantCategoryDto saveRestaurantCategory(NewRestaurantCategoryDto newRestaurantCategoryDTO) {
        RestaurantCategory savedRestaurantCategory = restaurantCategoryRepository
                .save(restaurantCategoryMapper.DtoToRestaurantCategory(newRestaurantCategoryDTO));
        return null;
    }

    public RestaurantCategoryDto updateRestaurantCategory(UUID id, NewRestaurantCategoryDto newRestaurantCategoryDTO) {
        RestaurantCategory updatedRestaurantCategory = restaurantCategoryRepository
                .save(restaurantCategoryMapper.DtoToRestaurantCategory(newRestaurantCategoryDTO, id));
        return null;
    }

    public void deleteRestaurantCategory(UUID id) {
        RestaurantCategory deletedRestaurantCategory = restaurantCategoryMapper.DtoToRestaurantCategory(id);
        restaurantCategoryRepository.delete(deletedRestaurantCategory);
    }
}
