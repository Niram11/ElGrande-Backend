package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    private UUID addressId;

    @BeforeEach
    void setUp() {
        addressId = UUID.fromString("8a814d44-51b4-4a9b-a3f2-ece1775c98e0");
    }

    @Test
    void testFindAll_ShouldReturnListOfAddresses_WhenCallingMethod() {
        // when
        List<Address> list = repository.findAll();

        // test
        assertEquals(list.size(), 2);
    }

    @Test
    void testFindById_ShouldReturnAddress_WhenExist() {
        // when
        Optional<Address> address = repository.findById(addressId);

        // test
        assertTrue(address.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoAddress() {
        // when
        Optional<Address> address = repository.findById(UUID.randomUUID());

        // test
        assertTrue(address.isEmpty());
    }

    @Test
    void testFindByRestaurantId_ShouldReturnAddress_WhenExist() {
        // given
        UUID restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // when
        Optional<Address> address = repository.findByRestaurantId(restaurantId);

        // test
        assertTrue(address.isPresent());
    }

    @Test
    void testFindByRestaurantId_ShouldReturnEmptyOptional_WhenNoAddress() {
        // given
        UUID restaurantId = UUID.fromString("3e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // when
        Optional<Address> address = repository.findByRestaurantId(restaurantId);

        // test
        assertTrue(address.isEmpty());
    }

    @Test
    void testSave_ShouldReturnSameAddress_WhenProvidingValidData() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.fromString("c728af54-0d03-4af1-a68e-6364db2370ee"));

        Address address = new Address();
        address.setCountry("PL");
        address.setCity("Wrocław");
        address.setPostalCode("66612");
        address.setStreet("Ulica1");
        address.setStreetNumber("11C");
        address.setRestaurant(restaurant);

        // when
        Address savedAddress = repository.save(address);
        Optional<Address> savedAddressById = repository.findById(savedAddress.getId());

        // test
        assertTrue(savedAddressById.isPresent());
        assertEquals(savedAddress, savedAddressById.get());
    }

    @Test
    void testSave_ShouldUpdateActualRecord_WhenProvidingExistingAddress() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.fromString("c728af54-0d03-4af1-a68e-6364db2370ee"));

        Address address = new Address();
        address.setId(addressId);
        address.setCountry("PL");
        address.setCity("Wrocław");
        address.setPostalCode("66612");
        address.setStreet("Ulica1");
        address.setStreetNumber("11C");
        address.setRestaurant(restaurant);

        // when
        Address afterSaveAddress = repository.save(address);

        // test
        assertEquals(afterSaveAddress.getId(), addressId);
        assertNotEquals(afterSaveAddress.getCountry(), "Germany");
        assertNotEquals(afterSaveAddress.getCity(), "Berlin");
        assertNotEquals(afterSaveAddress.getPostalCode(), "41579");
        assertNotEquals(afterSaveAddress.getStreet(), "street1");
        assertNotEquals(afterSaveAddress.getStreetNumber(), "16B");
        assertNotEquals(afterSaveAddress.getRestaurant().getId(), UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5"));
    }
}
