package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.AddressMapper;
import com.codecool.gastro.service.validation.AddressValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @InjectMocks
    AddressService service;
    @Mock
    AddressRepository repository;
    @Mock
    AddressMapper mapper;
    @Mock
    AddressValidation validation;

    private UUID addressId;
    private UUID restaurantId;
    private AddressDto addressDto;
    private Address address;
    private NewAddressDto newAddressDto;

    @BeforeEach
    void setUp() {
        addressId = UUID.fromString("5d0c91c3-9d20-4e24-84a2-04d420b86bc0");
        restaurantId = UUID.fromString("88cb2c8d-3156-47be-a81f-b0566c26c5c3");

        addressDto = new AddressDto(
                addressId,
                "Poland",
                "Łódź",
                "34-450",
                "Łużycka",
                "55/2",
                ""
        );

        newAddressDto = new NewAddressDto(
                "Poland",
                "Gdańsk",
                "12-345",
                "Warszawska",
                "13B/3",
                ""
        );

        address = new Address();
        address.setId(addressId);

    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnAddressDto_WhenExist() {
        // when
        when(validation.validateByRestaurantId(restaurantId)).thenReturn(address);
        when(mapper.toDto(address)).thenReturn(addressDto);

        // when
        AddressDto pickedAddress = service.getAddressByRestaurantId(restaurantId);

        // then
        assertEquals(address.getId(), pickedAddress.id());
    }

    @Test
    void testGetAddressByRestaurantId_ShouldThrowObjectNotFoundException_WhenNoAddress() {
        // when
        doThrow(ObjectNotFoundException.class).when(validation).validateByRestaurantId(restaurantId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.getAddressByRestaurantId(restaurantId));
    }

    @Test
    void testUpdateAddress_ShouldReturnAddressDto_WhenExist() {
        // when
        when(validation.validateEntityById(addressId)).thenReturn(address);
        when(repository.save(address)).thenReturn(address);
        when(mapper.toDto(any(Address.class))).thenReturn(addressDto);
        AddressDto savedAddressDto = service.updateAddress(addressId, newAddressDto);

        // then
        assertEquals(savedAddressDto, addressDto);
    }

    @Test
    void testUpdateAddress_ShouldThrowObjectNotFoundException_WhenNoAddress() {
        // when
        doThrow(ObjectNotFoundException.class).when(validation).validateEntityById(addressId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.updateAddress(addressId, newAddressDto));
    }
}
