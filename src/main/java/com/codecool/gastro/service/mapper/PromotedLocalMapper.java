package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.PromotedLocal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PromotedLocalMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    PromotedLocalDto promotedLocalToDto(PromotedLocal promotedLocal);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    PromotedLocal dtoToPromotedLocal(NewPromotedLocalDto newPromotedLocalDto);

    @Mapping(source = "newBusinessHourDto.restaurantId", target = "restaurant.id")
    @Mapping(source = "id", target = "id")
    PromotedLocal dtoToPromotedLocal(NewPromotedLocalDto newPromotedLocalDto, UUID id);

    @Mapping(source = "id", target = "id")
    PromotedLocal dtoToPromotedLocal(UUID id);
}
