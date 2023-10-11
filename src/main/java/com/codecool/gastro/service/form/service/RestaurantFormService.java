package com.codecool.gastro.service.form.service;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.form.handler.FormHandler;
import com.codecool.gastro.service.mapper.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantFormService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final LocationRepository locationRepository;
    private final BusinessHourRepository businessHourRepository;
    private final AddressRepository addressRepository;
    private final FormHandler<Address, NewAddressDto> addressHandler;
    private final FormHandler<BusinessHour, NewBusinessHourDto> businessHourHandler;
    private final FormHandler<Location, NewLocationDto> locationHandler;

    public RestaurantFormService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository,
                                 LocationRepository locationRepository, BusinessHourRepository businessHourRepository,
                                 AddressRepository addressRepository, FormHandler<Address, NewAddressDto> addressHandler,
                                 FormHandler<BusinessHour, NewBusinessHourDto> businessHourHandler,
                                 FormHandler<Location, NewLocationDto> locationHandler) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.locationRepository = locationRepository;
        this.businessHourRepository = businessHourRepository;
        this.addressRepository = addressRepository;
        this.addressHandler = addressHandler;
        this.businessHourHandler = businessHourHandler;
        this.locationHandler = locationHandler;
    }

    @Transactional
    public void submitRestaurantForm(NewRestaurantFormDto newFormDto) {
        Restaurant restaurant = restaurantMapper.dtoToRestaurant(newFormDto.restaurant());
        Address address = addressHandler.handleDto(newFormDto.address(), restaurant);
        Location location = locationHandler.handleDto(newFormDto.location(), restaurant);
        List<BusinessHour> businessHourList = newFormDto.businessHour().stream()
                .map(bh -> businessHourHandler.handleDto(bh, restaurant))
                .toList();

        saveForm(restaurant, location, businessHourList, address);
    }

    private void saveForm(Restaurant restaurant, Location location, List<BusinessHour> businessHourList, Address address) {
        restaurantRepository.save(restaurant);
        locationRepository.save(location);
        addressRepository.save(address);
        businessHourRepository.saveAll(businessHourList);
    }
}
