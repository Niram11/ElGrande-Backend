package com.codecool.gastro.service;


import com.codecool.gastro.DTO.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantrestaurantcategory.RestaurantRestaurantCategoryDTO;
import com.codecool.gastro.repository.RestaurantRestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantRestaurantCategory;
import com.codecool.gastro.service.mapper.RestaurantRestaurantCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantRestaurantCategoryService {

    private RestaurantRestaurantCategoryRepository restaurantRestaurantCategoryRepository;
    private RestaurantRestaurantCategoryMapper restaurantRestaurantCategoryMapper;

    public RestaurantRestaurantCategoryService(RestaurantRestaurantCategoryRepository
        restaurantRestaurantCategoryRepository, RestaurantRestaurantCategoryMapper restaurantRestaurantCategoryMapper) {
        this.restaurantRestaurantCategoryRepository = restaurantRestaurantCategoryRepository;
        this.restaurantRestaurantCategoryMapper = restaurantRestaurantCategoryMapper;
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
