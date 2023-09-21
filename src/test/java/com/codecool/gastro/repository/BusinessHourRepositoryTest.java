package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
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
    private BusinessHourRepository repository;

    @Test
    void testFindAll_ShouldReturnListOfBusinessHours_WhenCalled() {
        // then
        List<BusinessHour> list = repository.findAll();

        // test
        assertEquals(4, list.size());
    }

    @Test
    void testFindById_ShouldReturnBusinessHour_WhenExist() {
        // given
        UUID businessHourId = UUID.fromString("c56f5aec-de67-4698-b045-7e96ef64dad0");

        // then
        Optional<BusinessHour> businessHour = repository.findById(businessHourId);

        // test
        assertTrue(businessHour.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoBusinessHours() {
        // given
        UUID businessHourId = UUID.randomUUID();

        // then
        Optional<BusinessHour> businessHour = repository.findById(businessHourId);

        // test
        assertTrue(businessHour.isEmpty());
    }

    @Test
    void testFindAllByRestaurantId_ShouldReturnListOfBusinessHour_WhenExist() {
        // given
        UUID restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");

        // then
        List<BusinessHour> list = repository.findAllByRestaurantId(restaurantId);

        // test
        assertEquals(2, list.size());
    }

    @Test
    void testFindAllByRestaurantId_ShouldReturnEmptyList_WhenNoBusinessHour() {
        // given
        UUID restaurantId = UUID.randomUUID();

        // then
        List<BusinessHour> list = repository.findAllByRestaurantId(restaurantId);

        // test
        assertEquals(0, list.size());
    }

    @Test
    void testSave_ShouldSaveNewRecordToDataBase_WhenProvidingValidData() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.fromString("c728af54-0d03-4af1-a68e-6364db2370ee"));

        BusinessHour businessHour = new BusinessHour();
        businessHour.setDayOfWeek(5);
        businessHour.setOpeningHour(LocalTime.of(9, 15));
        businessHour.setClosingHour(LocalTime.of(17, 45));
        businessHour.setRestaurant(restaurant);

        // then
        BusinessHour savedBusinessHour = repository.save(businessHour);
        Optional<BusinessHour> businessHourById = repository.findById(savedBusinessHour.getId());

        // test
        assertTrue(businessHourById.isPresent());
    }

    @Test
    void testSave_ShouldSaveExistingRecord_WhenBusinessHourExist() {
        // given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.fromString("c728af54-0d03-4af1-a68e-6364db2370ee"));

        BusinessHour businessHour = new BusinessHour();
        businessHour.setId(UUID.fromString("c56f5aec-de67-4698-b045-7e96ef64dad0"));
        businessHour.setDayOfWeek(5);
        businessHour.setOpeningHour(LocalTime.of(9, 15));
        businessHour.setClosingHour(LocalTime.of(17, 45));
        businessHour.setRestaurant(restaurant);

        // then
        BusinessHour savedBusinessHour = repository.save(businessHour);
        BusinessHour businessHourById = repository.findById(savedBusinessHour.getId()).get();

        // test
        assertEquals(savedBusinessHour.getId(), businessHourById.getId());
        assertEquals(savedBusinessHour.getDayOfWeek(), businessHourById.getDayOfWeek());
        assertEquals(savedBusinessHour.getOpeningHour(), businessHourById.getOpeningHour());
        assertEquals(savedBusinessHour.getClosingHour(), businessHourById.getClosingHour());
        assertEquals(savedBusinessHour.getRestaurant(), businessHourById.getRestaurant());
    }

    @Test
    void testDelete_ShouldReturnOptionalEmpty_WhenFindingDeletedId() {
        // given
        UUID bhId = UUID.fromString("c56f5aec-de67-4698-b045-7e96ef64dad0");

        BusinessHour businessHour = new BusinessHour();
        businessHour.setId(bhId);

        // then
        repository.delete(businessHour);
        Optional<BusinessHour> deletedBusinessHour = repository.findById(bhId);

        // test
        assertTrue(deletedBusinessHour.isEmpty());
    }

}
