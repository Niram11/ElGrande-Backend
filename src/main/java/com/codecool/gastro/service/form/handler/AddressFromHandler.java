package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.form.handler.FormHandler;
import com.codecool.gastro.service.mapper.AddressMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressFromHandler implements FormHandler<Address, NewAddressDto> {
    private final AddressMapper addressMapper;

    public AddressFromHandler(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public Address handleDto(NewAddressDto newEntityDto, Restaurant restaurant) {
        Address address = addressMapper.dtoToAddress(newEntityDto);
        address.setRestaurant(restaurant);

        return address;
    }
}
