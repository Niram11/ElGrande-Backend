package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.entity.Address;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    Address dtoToAddress(NewAddressDto newAddressDto);

    Address dtoToAddress(UUID id);

    Address dtoToAddress(UUID id, NewAddressDto newAddressDto);
}
