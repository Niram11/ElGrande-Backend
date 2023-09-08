package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.entity.PromotedLocal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PromotedLocalMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    PromotedLocalDto toDto(PromotedLocal promotedLocal);

    @Mapping(target = "restaurant.id", source = "newPromotedLocalDto.restaurantId")
    PromotedLocal dtoToPromotedLocal(NewPromotedLocalDto newPromotedLocalDto);

    @Mapping(target = "restaurant.id", source = "newPromotedLocalDto.restaurantId")
    PromotedLocal dtoToPromotedLocal(UUID id, NewPromotedLocalDto newPromotedLocalDto);

    PromotedLocal dtoToPromotedLocal(UUID id);
}
