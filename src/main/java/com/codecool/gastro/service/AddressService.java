package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDto> getAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDto)
                .toList();
    }

    public AddressDto getAddressBy(UUID id) {
        return addressRepository.findOneBy(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }

    public AddressDto saveAddress(NewAddressDto newAddressDto) {
        Address savedAddress = addressRepository.save(addressMapper.dtoToAddress(newAddressDto));
        return addressMapper.toDto(savedAddress);
    }

    public AddressDto updateAddress(UUID id, NewAddressDto newAddressDto) {
        Address updatedAddress = addressRepository.save(addressMapper.dtoToAddress(id, newAddressDto));
        return addressMapper.toDto(updatedAddress);
    }

    public void deleteAddress(UUID id) {
        addressRepository.delete(addressMapper.dtoToAddress(id));
    }

}
