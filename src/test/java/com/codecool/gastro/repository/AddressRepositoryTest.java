package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import org.assertj.core.api.Assertions;
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
    AddressRepository repository;

    private UUID restaurantId;
    private UUID addressId;

    @BeforeEach
    void setUp() {
        addressId = UUID.fromString("8a814d44-51b4-4a9b-a3f2-ece1775c98e0");
        restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");
    }
    @Test
    void testFindById_ShouldReturnAddress_WhenExist() {
        // when
        Optional<Address> address = repository.findById(addressId);

        // then
        assertTrue(address.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoAddress() {
        // when
        Optional<Address> address = repository.findByRestaurantId(UUID.randomUUID());

        // then
        assertTrue(address.isEmpty());
    }

    @Test
    void testFindByRestaurantId_ShouldReturnAddress_WhenExist() {
        // when
        Optional<Address> address = repository.findByRestaurantId(restaurantId);

        // then
        assertTrue(address.isPresent());
    }

    @Test
    void testFindByRestaurantId_ShouldReturnEmptyOptional_WhenNoAddress() {
        // when
        Optional<Address> address = repository.findByRestaurantId(UUID.randomUUID());

        // then
        assertTrue(address.isEmpty());
    }
}
