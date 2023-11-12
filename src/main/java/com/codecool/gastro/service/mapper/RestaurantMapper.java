package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto toDto(Restaurant restaurant);

    DetailedRestaurantDto toDetailedDto(DetailedRestaurantProjection restaurant);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateRestaurantFromDto(NewRestaurantDto newRestaurantDto,@MappingTarget Restaurant restaurant);
}
