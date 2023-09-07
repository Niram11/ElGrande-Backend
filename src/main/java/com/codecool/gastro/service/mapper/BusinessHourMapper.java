package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessHourMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    BusinessHourDto businessHourToDto(BusinessHour businessHour);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    BusinessHour dtoToBusinessHour(NewBusinessHourDto newBusinessHourDto);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    @Mapping(source = "id", target = "id")
    BusinessHour dtoToBusinessHour(NewBusinessHourDto newBusinessHourDto, UUID id);

    @Mapping(source = "id", target = "id")
    BusinessHour dtoToBusinessHour(UUID id);
}