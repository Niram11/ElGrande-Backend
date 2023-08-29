package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.EntityNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    //TODO: soft delete?
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public List<RestaurantDTO> getRestaurants() {
        return restaurantRepository.findAllBy().stream()
                .map(restaurantMapper::restaurantToDTO).toList();
    }

    public RestaurantDTO getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::restaurantToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public RestaurantDTO saveNewRestaurant(NewRestaurantDTO newRestaurantDTO) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurantMapper.DTOToRestaurant(newRestaurantDTO));
        return restaurantMapper.restaurantToDTO(savedRestaurant);
    }

    public RestaurantDTO updateRestaurant(UUID id, NewRestaurantDTO newRestaurantDTO) {
        Restaurant updatedRestaurant = restaurantRepository.save(restaurantMapper.DTOToRestaurant(newRestaurantDTO, id));
        return restaurantMapper.restaurantToDTO(updatedRestaurant);
    }

    public void softDelete(UUID id) {
        Restaurant restaurant = restaurantRepository.findBy(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Restaurant.class));

        obfuscateData(restaurant);
        restaurantRepository.save(restaurant);
    }

    private void obfuscateData(Restaurant restaurant) {
        int nameLength = restaurant.getName().length();
        restaurant.setName("*".repeat(nameLength));

        int descriptionLength = restaurant.getDescription().length();
        restaurant.setDescription("*".repeat(descriptionLength));

        int websiteLength = restaurant.getWebsite().length();
        restaurant.setWebsite("*".repeat(websiteLength));

        int contactNumberLength = restaurant.getContactNumber().length();
        restaurant.setContactNumber("*".repeat(contactNumberLength));

        int contactEmailLength = restaurant.getContactEmail().length();
        restaurant.setContactEmail("*".repeat(contactEmailLength));
    }

}
