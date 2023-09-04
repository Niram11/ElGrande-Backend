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
        return restaurantRestaurantCategoryRepository.findAll().stream()
                .map(restaurantRestaurantCategoryMapper::restaurantRestaurantCategoryToDTO).toList();
    }

    public RestaurantRestaurantCategoryDTO getRestaurantRestaurantCategoryByUUID(UUID id) {
        return restaurantRestaurantCategoryRepository.findById(id)
                .map(restaurantRestaurantCategoryMapper::restaurantRestaurantCategoryToDTO)
                .orElseThrow(() -> new RuntimeException());
    }

    public RestaurantRestaurantCategoryDTO saveRestaurantRestaurantCategory(NewRestaurantRestaurantCategoryDTO
                                                                                    newRestaurantRestaurantCategoryDTO) {
        RestaurantRestaurantCategory savedRestaurantRestaurantCategory = restaurantRestaurantCategoryRepository
                .save(restaurantRestaurantCategoryMapper
                        .DTOToRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO));
        return restaurantRestaurantCategoryMapper.restaurantRestaurantCategoryToDTO(savedRestaurantRestaurantCategory);
    }

    public RestaurantRestaurantCategoryDTO updateRestaurantRestaurantCategory(UUID id, NewRestaurantRestaurantCategoryDTO
            newRestaurantRestaurantCategoryDTO) {
        RestaurantRestaurantCategory updatedRestaurantRestaurantCategory = restaurantRestaurantCategoryRepository
                .save(restaurantRestaurantCategoryMapper
                        .DTOToRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO, id));
        return restaurantRestaurantCategoryMapper.restaurantRestaurantCategoryToDTO(updatedRestaurantRestaurantCategory);
    }

    public void deleteRestaurantRestaurantCategory(UUID id) {
        RestaurantRestaurantCategory deletedRestaurantRestaurantCategory = restaurantRestaurantCategoryMapper
                .DTOToRestaurantRestaurantCategory(id);
        restaurantRestaurantCategoryRepository.delete(deletedRestaurantRestaurantCategory);
    }
}
