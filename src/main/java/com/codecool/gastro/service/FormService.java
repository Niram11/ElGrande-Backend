package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.NewFormAddressDto;
import com.codecool.gastro.dto.businesshour.NewFormBusinessHourDto;
import com.codecool.gastro.dto.form.NewFormRestaurantDto;
import com.codecool.gastro.dto.location.NewLocationDto;
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

import java.util.List;

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
    public void provideRestaurantForm(NewFormRestaurantDto newFormDto) {
        Restaurant restaurant = restaurantMapper.dtoToRestaurant(newFormDto.restaurant());
        Location location = handleLocation(newFormDto.location(), restaurant);
        List<BusinessHour> businessHours = handleBusinessHours(newFormDto.businessHour(), restaurant);
        Address address = handleAddress(newFormDto.address(), restaurant);
        saveForm(restaurant, location, businessHours, address);
    }

    private Location handleLocation(NewLocationDto locationDto, Restaurant restaurant){
        Location location = locationMapper.dtoToLocation(locationDto);
        location.assignRestaurant(restaurant);
        return location;
    }
    private Address handleAddress(NewFormAddressDto newFormAddressDto, Restaurant restaurant) {
        Address address = addressMapper.newFormDtoToAddress(newFormAddressDto);
        address.setRestaurant(restaurant);
        return address;
    }

    private List<BusinessHour> handleBusinessHours(List<NewFormBusinessHourDto> newFormBusinessHourDtoList, Restaurant restaurant) {
        List<BusinessHour> mappedBusinessHours = newFormBusinessHourDtoList
            .stream()
            .map(businessHourMapper::newFormDtoToBusinessHour)
            .toList();
        mappedBusinessHours.forEach(bh -> bh.setRestaurant(restaurant));
        return mappedBusinessHours;
    }

    private void saveForm(Restaurant restaurant, Location location, List<BusinessHour> businessHour, Address address) {
        restaurantRepository.save(restaurant);
        locationRepository.save(location);
        addressRepository.save(address);
        businessHourRepository.saveAll(businessHour);
    }
}
