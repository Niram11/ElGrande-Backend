package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressValidation implements Validation<UUID>{
    private final AddressRepository addressRepository;

    public AddressValidation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void validateEntityById(UUID id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }
    public void validateGetAddressByRestaurantId(UUID id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }
}
