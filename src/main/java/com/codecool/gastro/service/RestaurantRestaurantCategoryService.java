package com.codecool.gastro.service;


import com.codecool.gastro.DTO.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantrestaurantcategory.RestaurantRestaurantCategoryDTO;
import com.codecool.gastro.repository.RestaurantRestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantRestaurantCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantRestaurantCategoryService {

    private RestaurantRestaurantCategoryRepository restaurantRestaurantCategoryRepository;

    public RestaurantRestaurantCategoryService(RestaurantRestaurantCategoryRepository
                                                       restaurantRestaurantCategoryRepository) {
        this.restaurantRestaurantCategoryRepository = restaurantRestaurantCategoryRepository;
    }

    public List<RestaurantRestaurantCategoryDTO> getRestaurantRestaurantCategories() {
        return restaurantRestaurantCategoryRepository
    }

    public RestaurantRestaurantCategoryDTO getRestaurantRestaurantCategoryByUUID(UUID id) {
        return restaurantRestaurantCategoryRepository
    }

    public RestaurantRestaurantCategoryDTO saveRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDTO
                                                                                    newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryRepository
    }

    public RestaurantRestaurantCategoryDTO updateRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDTO
                                                                                      newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryRepository
    }

    public void deleteRestaurantRestaurantCategory(UUID id) {
        RestaurantRestaurantCategory deletedRestaurantRestaurantCategory =
    }
}
