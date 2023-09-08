package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {


    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "promotedLocalId", source = "promotedLocal.id")
    RestaurantDto toDto(Restaurant restaurant);

    @Mapping(target = "customer.id", source = "customerId")
//    @Mapping(target = "location.id", source = "locationId")
    Restaurant dtoToRestaurant(NewRestaurantDto newRestaurantDto);

    @Mapping(target = "customer.id", source = "newRestaurantDto.customerId")
//    @Mapping(target = "location.id", source = "newRestaurantDto.locationId")
    Restaurant dtoToRestaurant(NewRestaurantDto newRestaurantDto, UUID id);
}
