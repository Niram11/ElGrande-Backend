package com.codecool.gastro.service;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.AddressMapper;
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
    private AddressService service;

    @Mock
    private AddressRepository repository;

    @Mock
    private AddressMapper mapper;


    private UUID addressId;
    private UUID restaurantId;
    private AddressDto addressDto;
    private Address address;
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

        address = new Address();
    }

    @Test
    void testGetAddresses_ShouldReturnEmptyList_WhenNoAddresses() {
        // when
        when(repository.findAll()).thenReturn(List.of());

        // then
        List<AddressDto> list = service.getAddresses();

        // test
        assertEquals(0, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAddresses_ShouldReturnListOfAddressesDto_WhenAddressesExist() {
        // given
        AddressDto addressDtoOne = addressDto;

        AddressDto addressDtoTwo = new AddressDto(
                UUID.randomUUID(),
                "Germany",
                "Hamburg",
                "34450",
                "street",
                "55/2",
                ""
        );

        Address addressOne = address;
        Address addressTwo = new Address();

        // when
        when(repository.findAll()).thenReturn(List.of(addressOne, addressTwo));
        when(mapper.toDto(addressOne)).thenReturn(addressDtoOne);
        when(mapper.toDto(addressTwo)).thenReturn(addressDtoTwo);

        // then
        List<AddressDto> list = service.getAddresses();

        // test
        assertEquals(List.of(addressDtoOne, addressDtoTwo), list);
        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(Address.class));
    }

    @Test
    void testGetAddressById_ShouldReturnAddressDto_WhenAddressExist() {
        // given
        address.setId(addressId);

        // when
        when(repository.findById(address.getId())).thenReturn(Optional.of(address));
        when(mapper.toDto(any(Address.class))).thenReturn(addressDto);

        // then
        AddressDto pickedAddress = service.getAddressById(address.getId());

        // test
        assertEquals(address.getId(), pickedAddress.id());
        verify(repository, times(1)).findById(any(UUID.class));
        verify(mapper, times(1)).toDto(any(Address.class));
    }

    @Test
    void testGetAddressById_ShouldThrowObjectNotFoundException_WhenNoAddress() {
        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getAddressById(UUID.randomUUID()));
    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnAddressDto_WhenAddressExist() {
        // given
        address.setId(addressId);

        // when
        when(repository.findByRestaurantId(restaurantId)).thenReturn(Optional.of(address));
        when(mapper.toDto(any(Address.class))).thenReturn(addressDto);

        // then
        AddressDto pickedAddress = service.getAddressByRestaurantId(restaurantId);

        // test
        assertEquals(address.getId(), pickedAddress.id());
        verify(repository, times(1)).findByRestaurantId(any(UUID.class));
        verify(mapper, times(1)).toDto(any(Address.class));
    }

    @Test
    void testGetAddressByRestaurantId_ShouldThrowObjectNotFoundException_WhenNoAddress() {
        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getAddressByRestaurantId(UUID.randomUUID()));
    }


    @Test
    void testSaveNewAddressAndUpdateAddress_ShouldReturnAddressDto_WhenSavingAnInstance() {
        // given
        NewAddressDto newAddressDto = new NewAddressDto(
                "Poland",
                "Gdańsk",
                "12-345",
                "Warszawska",
                "13B/3",
                "",
                restaurantId
        );

        // when
        when(mapper.dtoToAddress(newAddressDto)).thenReturn(address);
        when(repository.save(address)).thenReturn(address);
        when(mapper.toDto(any(Address.class))).thenReturn(addressDto);

        // then
        AddressDto savedAddressDto = service.saveNewAddress(newAddressDto);

        // test
        assertEquals(savedAddressDto, addressDto);
        verify(mapper, times(1)).dtoToAddress(any(NewAddressDto.class));
        verify(repository, times(1)).save(any(Address.class));
        verify(mapper, times(1)).toDto(any(Address.class));
    }

    @Test
    void testDeleteAddress_ShouldDeleteAddress_WhenCalled() {
        // given
        address.setId(addressId);

        // when
        when(mapper.dtoToAddress(addressId)).thenReturn(address);

        // then
        service.deleteAddress(addressId);

        // test
        verify(mapper, times(1)).dtoToAddress(addressId);
        verify(repository, times(1)).delete(address);
    }

}
