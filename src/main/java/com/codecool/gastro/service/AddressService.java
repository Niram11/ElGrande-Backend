package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.validation.AddressValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AddressValidation validation;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper,
                          AddressValidation validation) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.validation = validation;
    }

    public AddressDto getAddressByRestaurantId(UUID restaurantId) {
        return addressMapper.toDto(validation.validateByRestaurantId(restaurantId));
    }

    public AddressDto updateAddress(UUID id, NewAddressDto newAddressDto) {
        Address updatedAddress = validation.validateEntityById(id);
        addressMapper.updateAddressFromDto(newAddressDto, updatedAddress);
        return addressMapper.toDto(addressRepository.save(updatedAddress));
    }
}
