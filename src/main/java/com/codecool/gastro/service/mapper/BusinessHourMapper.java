package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessHourMapper {

    @Mapping(target = "restaurantId", source = "businessHour.restaurant.id")
    BusinessHourDto businessHourToDto(BusinessHour businessHour);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    BusinessHour DtoToBusinessHour(NewBusinessHourDto newBusinessHourDto);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    @Mapping(source = "id", target = "id")
    BusinessHour DtoToBusinessHour(NewBusinessHourDto newBusinessHourDto, UUID id);

    @Mapping(source = "id", target = "id")
    BusinessHour DtoToBusinessHour(UUID id);
}
