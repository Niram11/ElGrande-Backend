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
        return restaurantRepository.findAllBy().stream()
                .map(restaurantMapper::restaurantToDto).toList();
    }

    public RestaurantDto getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::restaurantToDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }

    public RestaurantDto saveNewRestaurant(NewRestaurantDto newRestaurantDto) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurantMapper.dtoToRestaurant(newRestaurantDto));
        return restaurantMapper.restaurantToDto(savedRestaurant);
    }

    public RestaurantDto updateRestaurant(UUID id, NewRestaurantDto newRestaurantDto) {
        Restaurant updatedRestaurant = restaurantRepository.save(restaurantMapper.dtoToRestaurant(newRestaurantDto, id));
        return restaurantMapper.restaurantToDto(updatedRestaurant);
    }

    public void softDelete(UUID id) {
        Restaurant restaurant = restaurantRepository.findBy(id)
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

        String[] contactEmail = restaurant.getContactEmail().split("@");
        restaurant.setContactEmail(UUID.randomUUID() + "*".repeat(contactEmail[0].length()) + contactEmail[1]);
    }

}
