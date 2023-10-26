package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.validation.AddressValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AddressValidation validation;
    private final RestaurantValidation restaurantValidation;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, AddressValidation validation,
                          RestaurantValidation restaurantValidation) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.validation = validation;
        this.restaurantValidation = restaurantValidation;
    }

    public AddressDto getAddressByRestaurantId(UUID restaurantId) {
        restaurantValidation.validateEntityById(restaurantId);
        return addressRepository.findByRestaurantId(restaurantId)
                .map(addressMapper::toDto).get();
    }

    public AddressDto updateAddress(UUID id, NewAddressDto newAddressDto) {
        validation.validateEntityById(id);
        Address updatedAddress = addressRepository.findById(id).get();
        addressMapper.updateAddressFromDto(newAddressDto, updatedAddress);
        return addressMapper.toDto(addressRepository.save(updatedAddress));
    }
}
