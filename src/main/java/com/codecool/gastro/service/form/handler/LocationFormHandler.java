package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.LocationRepository;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.LocationMapper;
import org.springframework.stereotype.Component;

@Component
public class LocationFormHandler implements FormHandler<Location> {
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public LocationFormHandler(LocationMapper locationMapper, LocationRepository locationRepository) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }

    @Override
    public void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant) {
        locationRepository.findByCoordinates(formDto.location().latitude(), formDto.location().longitude())
                .ifPresentOrElse(location -> {
                    location.assignRestaurant(restaurant);
                    locationRepository.save(location);
                }, () -> {
                    Location location = locationMapper.dtoToLocation(formDto.location());
                    location.assignRestaurant(restaurant);
                    locationRepository.save(location);
                });
    }
}
