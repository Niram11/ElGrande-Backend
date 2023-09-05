package com.codecool.gastro.service;


import com.codecool.gastro.dto.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantrestaurantcategory.RestaurantRestaurantCategoryDto;
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

    public List<RestaurantRestaurantCategoryDto> getRestaurantRestaurantCategories() {
        return restaurantRestaurantCategoryRepository.findAll().stream()
                .map(restaurantRestaurantCategoryMapper::restaurantRestaurantCategoryToDto).toList();
    }

    public RestaurantRestaurantCategoryDto getRestaurantRestaurantCategoryByUUID(UUID id) {
        return restaurantRestaurantCategoryRepository.findById(id)
                .map(restaurantRestaurantCategoryMapper::restaurantRestaurantCategoryToDto)
                .orElseThrow(() -> new RuntimeException());
    }

    public RestaurantRestaurantCategoryDto saveRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDto
                                                                                    newRestaurantRestaurantCategoryDTO) {
        RestaurantRestaurantCategory savedRestaurantRestaurantCategory = restaurantRestaurantCategoryRepository
                .save(restaurantRestaurantCategoryMapper
                        .DtoToRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO));
        return restaurantRestaurantCategoryMapper.restaurantRestaurantCategoryToDto(savedRestaurantRestaurantCategory);
    }

    public RestaurantRestaurantCategoryDto updateRestaurantRestaurantCategory(UUID id, NewRestaurantRestaurantCategoryDto
            newRestaurantRestaurantCategoryDTO) {
        RestaurantRestaurantCategory updatedRestaurantRestaurantCategory = restaurantRestaurantCategoryRepository
                .save(restaurantRestaurantCategoryMapper
                        .DtoToRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO, id));
        return restaurantRestaurantCategoryMapper.restaurantRestaurantCategoryToDto(updatedRestaurantRestaurantCategory);
    }

    public void deleteRestaurantRestaurantCategory(UUID id) {
        RestaurantRestaurantCategory deletedRestaurantRestaurantCategory = restaurantRestaurantCategoryMapper
                .DtoToRestaurantRestaurantCategory(id);
        restaurantRestaurantCategoryRepository.delete(deletedRestaurantRestaurantCategory);
    }
}