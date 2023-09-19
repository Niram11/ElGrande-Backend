package com.codecool.gastro.service;

import com.codecool.gastro.dto.form.NewFormDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.service.mapper.LocationMapper;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final BusinessHourRepository businessHourRepository;
    private final BusinessHourMapper businessHourMapper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public FormService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper,
                       LocationRepository locationRepository, LocationMapper locationMapper,
                       BusinessHourRepository businessHourRepository, BusinessHourMapper businessHourMapper,
                       AddressRepository addressRepository, AddressMapper addressMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.businessHourRepository = businessHourRepository;
        this.businessHourMapper = businessHourMapper;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public void provideForm(NewFormDto newFormDto) {
        Restaurant restaurant = restaurantMapper.dtoToRestaurant(newFormDto.newRestaurantDto());
        Location location = locationMapper.dtoToLocation(newFormDto.newLocationDto());
        BusinessHour businessHour = businessHourMapper.dtoToBusinessHour(newFormDto.newBusinessHourDto());
        businessHour.setRestaurant(restaurant);
        Address address = addressMapper.dtoToAddress(newFormDto.newAddressDto());
        address.setRestaurant(restaurant);
        saveForm(restaurant, location, businessHour, address);
    }

    private void saveForm(Restaurant restaurant, Location location, BusinessHour businessHour, Address address) {
        restaurantRepository.save(restaurant);
        locationRepository.save(location);
        businessHourRepository.save(businessHour);
        addressRepository.save(address);
    }
}
