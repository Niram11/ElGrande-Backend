package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.repository.entity.BusinessHour;
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

    @Mapping(source = "id", target = "id")
    BusinessHour DTOToBusinessHour(UUID id);
}
