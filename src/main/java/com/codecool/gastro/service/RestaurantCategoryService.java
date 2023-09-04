package com.codecool.gastro.service;


import com.codecool.gastro.DTO.restaurantcategory.NewRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantcategory.RestaurantCategoryDTO;
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

    public List<RestaurantCategoryDTO> getRestaurantCategories() {
        return restaurantCategoryRepository.findAll().stream().map(restaurantCategoryMapper::restaurantCategoryToDTO)
                .toList();
    }

    public RestaurantCategoryDTO getRestaurantCategoryByUUID(UUID id) {
        return restaurantCategoryRepository.findById(id).map(restaurantCategoryMapper::restaurantCategoryToDTO)
                .orElseThrow(() -> new RuntimeException());
    }

    public RestaurantCategoryDTO saveRestaurantCategory(NewRestaurantCategoryDTO newRestaurantCategoryDTO) {
        RestaurantCategory savedRestaurantCategory = restaurantCategoryRepository
                .save(restaurantCategoryMapper.DTOToRestaurantCategory(newRestaurantCategoryDTO));
        return null;
    }

    public RestaurantCategoryDTO updateRestaurantCategory(UUID id, NewRestaurantCategoryDTO newRestaurantCategoryDTO) {
        RestaurantCategory updatedRestaurantCategory = restaurantCategoryRepository
                .save(restaurantCategoryMapper.DTOToRestaurantCategory(newRestaurantCategoryDTO, id));
        return null;
    }

    public void deleteRestaurantCategory(UUID id) {
        RestaurantCategory deletedRestaurantCategory = restaurantCategoryMapper.DTOToRestaurantCategory(id);
        restaurantCategoryRepository.delete(deletedRestaurantCategory);
    }
}
