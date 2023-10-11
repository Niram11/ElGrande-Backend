package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.form.handler.FormHandler;
import com.codecool.gastro.service.mapper.LocationMapper;
import org.springframework.stereotype.Component;

@Component
public class LocationFormHandler implements FormHandler<Location, NewLocationDto> {
    private final LocationMapper locationMapper;

    public LocationFormHandler(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    @Override
    public Location handleDto(NewLocationDto newLocationDto, Restaurant restaurant) {
        Location location = locationMapper.dtoToLocation(newLocationDto);
        location.assignRestaurant(restaurant);

        return location;
    }
}
