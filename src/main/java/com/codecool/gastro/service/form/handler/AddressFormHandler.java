package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.AddressMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressFormHandler implements FormHandler<Address> {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public AddressFormHandler(AddressMapper addressMapper, AddressRepository addressRepository) {
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant) {
        Address address = addressMapper.dtoToAddress(formDto.address());
        address.setRestaurant(restaurant);

        addressRepository.save(address);

    }
}
