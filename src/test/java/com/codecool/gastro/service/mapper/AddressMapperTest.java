package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void testToDto_ShouldMapAddressToDto_WhenProvidingValidData() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setCountry("PL");
        address.setCity("Wrocław");
        address.setPostalCode("66612");
        address.setStreet("Ulica1");
        address.setStreetNumber("11C");
        address.setRestaurant(restaurant);

        // when
        AddressDto addressDto = mapper.toDto(address);

        // test
        assertEquals(addressDto.id(), address.getId());
        assertEquals(addressDto.country(), address.getCountry());
        assertEquals(addressDto.city(), address.getCity());
        assertEquals(addressDto.postalCode(), address.getPostalCode());
        assertEquals(addressDto.street(), address.getStreet());
        assertEquals(addressDto.streetNumber(), address.getStreetNumber());

    }

    @Test
    void testDtoToAddress_ShouldMapToAddress_WhenProvidingValidData() {
        // given
        UUID restaurantId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        NewAddressDto newAddressDto = new NewAddressDto(
                "Poland",
                "Opole",
                "78900",
                "Warszawska",
                "55Z",
                "",
                restaurantId
                );

        // when
        Address addressOne = mapper.dtoToAddress(newAddressDto);
        Address addressTwo = mapper.dtoToAddress(addressId, newAddressDto);

        // test
        assertEquals(newAddressDto.country(), addressOne.getCountry());
        assertEquals(newAddressDto.city(), addressOne.getCity());
        assertEquals(newAddressDto.postalCode(), addressOne.getPostalCode());
        assertEquals(newAddressDto.street(), addressOne.getStreet());
        assertEquals(newAddressDto.streetNumber(), addressOne.getStreetNumber());
        assertEquals(newAddressDto.restaurantId(), addressOne.getRestaurant().getId());

        assertEquals(addressId, addressTwo.getId());
        assertEquals(newAddressDto.country(), addressTwo.getCountry());
        assertEquals(newAddressDto.city(), addressTwo.getCity());
        assertEquals(newAddressDto.postalCode(), addressTwo.getPostalCode());
        assertEquals(newAddressDto.street(), addressTwo.getStreet());
        assertEquals(newAddressDto.streetNumber(), addressTwo.getStreetNumber());
        assertEquals(newAddressDto.restaurantId(), addressTwo.getRestaurant().getId());

    }
}
