package com.codecool.gastro.service;

import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.mapper.RestaurantMapper;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
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
}
