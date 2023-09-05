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

    PromotedLocalDto promotedLocalToDto(PromotedLocal promotedLocal);

    PromotedLocal dtoToPromotedLocal(NewPromotedLocalDto newPromotedLocalDto);

    PromotedLocal dtoToPromotedLocal(NewPromotedLocalDto newPromotedLocalDto, UUID id);

    PromotedLocal dtoToPromotedLocal(UUID id);
}
