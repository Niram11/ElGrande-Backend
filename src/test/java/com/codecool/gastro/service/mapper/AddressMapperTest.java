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
    AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    private Restaurant restaurant;
    private UUID restaurantId;
    private UUID addressId;
    private Address address;
    private NewAddressDto newAddressDto;

    @BeforeEach
    void setUp() {
        addressId = UUID.randomUUID();

        address = new Address();
        address.setId(addressId);
        address.setCountry("PL");
        address.setCity("Wrocław");
        address.setPostalCode("66612");
        address.setStreet("Ulica1");
        address.setStreetNumber("11C");

        newAddressDto = new NewAddressDto(
                "Poland",
                "Opole",
                "78900",
                "Warszawska",
                "55Z",
                ""
        );
    }

    @Test
    void testToDto_ShouldMapAddressToDto_WhenProvidingValidData() {
        // when
        AddressDto testedAddressDto = mapper.toDto(address);

        // then
        assertEquals(testedAddressDto.id(), address.getId());
        assertEquals(testedAddressDto.country(), address.getCountry());
        assertEquals(testedAddressDto.city(), address.getCity());
        assertEquals(testedAddressDto.postalCode(), address.getPostalCode());
        assertEquals(testedAddressDto.street(), address.getStreet());
        assertEquals(testedAddressDto.streetNumber(), address.getStreetNumber());

    }

    @Test
    void testDtoToAddress_ShouldMapToAddress_WhenProvidingDto() {
        // when
        Address testedAddress = mapper.dtoToAddress(newAddressDto);

        // then
        assertEquals(newAddressDto.country(), testedAddress.getCountry());
        assertEquals(newAddressDto.city(), testedAddress.getCity());
        assertEquals(newAddressDto.postalCode(), testedAddress.getPostalCode());
        assertEquals(newAddressDto.street(), testedAddress.getStreet());
        assertEquals(newAddressDto.streetNumber(), testedAddress.getStreetNumber());
    }

    @Test
    void testDtoToAddress_ShouldMapToAddress_WhenProvidingIdAndDto() {
        // when
        mapper.updateAddressFromDto(newAddressDto, address);

        // then
        assertEquals(addressId, address.getId());
        assertEquals(newAddressDto.country(), address.getCountry());
        assertEquals(newAddressDto.city(), address.getCity());
        assertEquals(newAddressDto.postalCode(), address.getPostalCode());
        assertEquals(newAddressDto.street(), address.getStreet());
        assertEquals(newAddressDto.streetNumber(), address.getStreetNumber());
    }
}
