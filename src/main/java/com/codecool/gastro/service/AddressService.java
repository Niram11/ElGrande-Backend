package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDto getAddressByRestaurantId(UUID restaurantId) {
        return addressRepository.findByRestaurantId(restaurantId)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantId, Restaurant.class));
    }

    public AddressDto updateAddress(UUID id, NewAddressDto newAddressDto) {
        Address updatedAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));

        addressMapper.updateAddressFromDto(newAddressDto, updatedAddress);
        return addressMapper.toDto(addressRepository.save(updatedAddress));
    }
}
