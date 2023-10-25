package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.repository.entity.Ownership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OwnershipMapper {
    OwnershipDto toDto(Ownership ownership);

    @Mapping(source = "customerId", target = "customer.id")
    Ownership dtoToOwnership(NewOwnershipDto ownershipDto);

    Ownership dtoToOwnership(UUID id);

    @Mapping(source = "ownershipDto.customerId", target = "customer.id")
    Ownership dtoToOwnership(NewOwnershipDto ownershipDto, UUID id);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    void updateOwnershipFromDto(NewOwnershipDto newOwnershipDto, @MappingTarget Ownership updatedOwnership);
}