package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.UUID;

public class AddressValidation implements Validation<UUID>{
    private final AddressRepository addressRepository;

    public AddressValidation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void validateUpdate(UUID id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }
    public void validateGetAddressByRestaurantId(UUID id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }
}
