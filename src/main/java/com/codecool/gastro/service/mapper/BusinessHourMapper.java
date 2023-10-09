package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessHourMapper {

    BusinessHourDto toDto(BusinessHour businessHour);

    @Mapping(target = "isUnknown", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    BusinessHour dtoToBusinessHour(NewBusinessHourDto newBusinessHourDto);

    @Mapping(target = "isUnknown", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "openingHour", ignore = true)
    @Mapping(target = "dayOfWeek", ignore = true)
    @Mapping(target = "closingHour", ignore = true)
    @Mapping(target = "id", source = "id")
    BusinessHour dtoToBusinessHour(UUID id);

    @Mapping(target = "isUnknown", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateBusinessHourFromDto(NewBusinessHourDto newBusinessHourDto, @MappingTarget BusinessHour businessHour);
}
