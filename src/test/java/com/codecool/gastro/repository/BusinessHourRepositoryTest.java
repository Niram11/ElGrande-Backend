package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BusinessHourRepositoryTest {
    @Autowired
    BusinessHourRepository repository;

    private UUID restaurantId;
    private UUID businessHourId;

    @BeforeEach
    void setUp() {
        restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");
        businessHourId = UUID.fromString("c56f5aec-de67-4698-b045-7e96ef64dad0");
    }

    @Test
    void testFindById_ShouldReturnBusinessHour_WhenExist() {
        // when
        Optional<BusinessHour> businessHour = repository.findById(businessHourId);

        // then
        assertTrue(businessHour.isPresent());
    }

    @Test
    void testFindById_ShouldReturnOptionalEmpty_WhenNoBusinessHour() {
        // when
        Optional<BusinessHour> businessHour = repository.findById(UUID.randomUUID());

        // then
        assertTrue(businessHour.isEmpty());
    }

    @Test
    void testFindAllByRestaurantId_ShouldReturnListOfBusinessHour_WhenExist() {
        // when
        List<BusinessHour> list = repository.findAllByRestaurantId(restaurantId);

        // then
        assertEquals(2, list.size());
    }

    @Test
    void testFindAllByRestaurantId_ShouldReturnEmptyList_WhenNoBusinessHour() {
        // when
        List<BusinessHour> list = repository.findAllByRestaurantId(UUID.randomUUID());

        // then
        assertEquals(0, list.size());
    }

}
