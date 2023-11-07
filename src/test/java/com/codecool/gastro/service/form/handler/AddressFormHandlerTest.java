package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.AddressRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressFormHandlerTest {
    FormHandler<Address> formHandler;
    @Mock
    AddressMapper addressMapper;
    @Mock
    AddressRepository addressRepository;

    private Address address;
    private Restaurant restaurant;
    private NewRestaurantFormDto formDto;

    @BeforeEach
    void setUp() {
        formHandler = new AddressFormHandler(
                addressMapper,
                addressRepository
        );

        NewAddressDto newAddressDto = new NewAddressDto(
                "",
                "",
                "",
                "",
                "",
                ""
        );

        formDto = new NewRestaurantFormDto(
                null,
                null,
                null,
                newAddressDto,
                UUID.randomUUID()
        );

        address = new Address();
        restaurant = new Restaurant();
    }

    @Captor
    private ArgumentCaptor<Address> captor;

    @Test
    void testHandleRestaurantForm_ShouldCreateNewAddressAndSetRestaurantToIt_WhenCalled() {
        // when
        when(addressMapper.dtoToAddress(any(NewAddressDto.class))).thenReturn(address);
        formHandler.handleRestaurantForm(formDto, restaurant);

        // then
        verify(addressRepository, times(1)).save(captor.capture());
        assertNotNull(captor.getValue().getRestaurant());
    }
}
