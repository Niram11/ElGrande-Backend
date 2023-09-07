package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.repository.entity.Ownership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OwnershipMapper
{
    @Mapping(source = "customer.id", target = "customerId")
    // @TODO check set to set behaviour
    @Mapping(source = "restaurants", target = "restaurantsIdsSet")
    OwnershipDto ownershipToDto(Ownership ownership);

    @Mapping(source = "customerId", target = "customer.id")
    Ownership dtoToOwnership(NewOwnershipDto ownershipDto);

    Ownership dtoToOwnership(UUID id);

    @Mapping(source = "customerId", target = "customer.id")
    Ownership dtoToOwnership(NewOwnershipDto ownershipDto, UUID id);

}
