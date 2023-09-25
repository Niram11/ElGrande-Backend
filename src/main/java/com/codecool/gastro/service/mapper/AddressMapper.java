package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.address.NewFormAddressDto;
import com.codecool.gastro.repository.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    @Mapping(target = "restaurant.id", source = "restaurantId")
    Address dtoToAddress(NewAddressDto newAddressDto);

    Address dtoToAddress(UUID id);

    @Mapping(target = "restaurant.id", source = "newAddressDto.restaurantId")
    Address dtoToAddress(UUID id, NewAddressDto newAddressDto);

    Address newFormDtoToAddress(NewFormAddressDto newFormAddressDto);
}
