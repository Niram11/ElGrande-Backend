package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewFormBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessHourMapper {

    BusinessHourDto toDto(BusinessHour businessHour);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    BusinessHour dtoToBusinessHour(NewBusinessHourDto newBusinessHourDto);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    BusinessHour dtoToBusinessHour(NewBusinessHourDto newBusinessHourDto, UUID id);

    BusinessHour dtoToBusinessHour(UUID id);

    BusinessHour newFormDtoToBusinessHour(NewFormBusinessHourDto newFormBusinessHourDto);
}
