package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressValidation implements Validation<Address> {
    private final AddressRepository addressRepository;

    public AddressValidation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address validateEntityById(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Address.class));
    }

    public Address validateByRestaurantId(UUID restaurantId) {
        return addressRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantId, Restaurant.class));
    }
}
