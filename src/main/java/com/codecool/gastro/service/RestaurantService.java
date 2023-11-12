package com.codecool.gastro.service;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.specification.FilteredRestaurantsSpecification;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import com.codecool.gastro.service.validation.RestaurantValidation;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final EntityManager entityManager;
    private final RestaurantValidation validation;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper,
                             EntityManager entityManager, RestaurantValidation validation) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.entityManager = entityManager;
        this.validation = validation;
    }

    public DetailedRestaurantDto getRestaurantById(UUID id) {
        return restaurantRepository.findDetailedRestaurantById(id)
                .map(restaurantMapper::toDetailedDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Restaurant.class));
    }

    public List<RestaurantDto> getRestaurantByCustomerId(UUID customerId) {
        return restaurantRepository.findAllByCustomerId(customerId)
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public List<RestaurantDto> getRestaurantByOwnershipId(UUID ownershipId) {
        return restaurantRepository.findAllByOwnershipId(ownershipId)
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public List<DetailedRestaurantDto> getDetailedRestaurants(Pageable pageable) {
        return restaurantRepository.findAllDetailedRestaurants(pageable)
                .stream()
                .map(restaurantMapper::toDetailedDto)
                .toList();
    }

    public List<RestaurantDto> getFilteredRestaurants(FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        FilteredRestaurantsSpecification specification = new FilteredRestaurantsSpecification(entityManager);
        return specification.getFilteredRestaurants(filteredRestaurantsCriteria)
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    public RestaurantDto updateRestaurant(UUID id, NewRestaurantDto newRestaurantDto) {
        Restaurant updatedRestaurant = validation.validateEntityById(id);
        restaurantMapper.updateRestaurantFromDto(newRestaurantDto, updatedRestaurant);
        return restaurantMapper.toDto(restaurantRepository.save(updatedRestaurant));
    }

    public void softDelete(UUID id) {
        Restaurant restaurant = validation.validateEntityById(id);
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
