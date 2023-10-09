package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    Address dtoToAddress(NewAddressDto newAddressDto);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateAddressFromDto(NewAddressDto newAddressDto, @MappingTarget Address address);

}
