package com.codecool.gastro.mapper;

import com.codecool.gastro.controler.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.controler.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessHourMapper {

    @Mapping(target = "restaurantId", source = "businessHour.restaurant.id")
    BusinessHourDTO businessHourToDTO(BusinessHour businessHour);

    @Mapping(source = "newBusinessHourDTO.restaurantId", target = "restaurant.id")
    BusinessHour DTOToBusinessHour(NewBusinessHourDTO newBusinessHourDTO);

    @Mapping(source = "newBusinessHourDTO.restaurantId", target = "restaurant.id")
    @Mapping(source = "id", target = "id")
    BusinessHour DTOToBusinessHour(NewBusinessHourDTO newBusinessHourDTO, UUID id);
}
