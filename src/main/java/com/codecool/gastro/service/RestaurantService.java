package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public List<RestaurantDto> getRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public RestaurantDto getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }

    public RestaurantDto saveNewRestaurant(NewRestaurantDto newRestaurantDto) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurantMapper.dtoToRestaurant(newRestaurantDto));
        return restaurantMapper.toDto(savedRestaurant);
    }

    public RestaurantDto updateRestaurant(UUID id, NewRestaurantDto newRestaurantDto) {
        Restaurant updatedRestaurant = restaurantRepository.save(restaurantMapper.dtoToRestaurant(newRestaurantDto, id));
        return restaurantMapper.toDto(updatedRestaurant);
    }

    public void softDelete(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));

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

        restaurant.setContactNumber(0);
        restaurant.setDeleted(true);

        String emailSuffix = restaurant.getContactEmail().split("@")[1];
        restaurant.setContactEmail(UUID.randomUUID() + "@" + emailSuffix);
    }

}
