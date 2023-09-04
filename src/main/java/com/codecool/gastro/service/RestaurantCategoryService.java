package com.codecool.gastro.service;


import com.codecool.gastro.DTO.restaurantcategory.NewRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantcategory.RestaurantCategoryDTO;
import com.codecool.gastro.repository.RestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantCategoryService {

    private RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
    }

    public List<RestaurantCategoryDTO> getRestaurantCategories() {
        return restaurantCategoryRepository
    }

    public RestaurantCategoryDTO getRestaurantCategoryByUUID(UUID id) {
        return restaurantCategoryRepository
    }

    public RestaurantCategoryDTO saveRestaurantCategory(NewRestaurantCategoryDTO newRestaurantCategoryDTO) {
        RestaurantCategory savedRestaurantCategory =
        return null;
    }

    public RestaurantCategoryDTO updateRestaurantCategory(UUID id, NewRestaurantCategoryDTO newRestaurantCategoryDTO) {
        RestaurantCategory updatedRestaurantCategory =
        return null;
    }

    public void deleteRestaurantCategory(UUID id) {
        RestaurantCategory deletedRestaurantCategory =
    }
}
