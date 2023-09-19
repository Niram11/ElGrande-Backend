package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessHourMapperTest {
    private final BusinessHourMapper mapper = Mappers.getMapper(BusinessHourMapper.class);

    @Test
    void testToDto_ShouldReturnMappedBusinessHour_WhenCalled() {
        // given
        BusinessHour businessHour = new BusinessHour();
        businessHour.setId(UUID.randomUUID());
        businessHour.setDayOfWeek(1);
        businessHour.setOpeningHour(LocalTime.of(12, 30));
        businessHour.setClosingHour(LocalTime.of(15, 50));

        // then
        BusinessHourDto businessHourDto = mapper.toDto(businessHour);

        // test
        assertEquals(businessHourDto.id(), businessHour.getId());
        assertEquals(businessHourDto.dayOfWeek(), businessHour.getDayOfWeek());
        assertEquals(businessHourDto.openingHour(), businessHour.getOpeningHour());
        assertEquals(businessHourDto.closingHour(), businessHour.getClosingHour());
    }

    @Test
    void testDtoToBusinessHours_ShouldReturnMappedBusinessHours_WhenCalled() {
        // given
        UUID restaurantId = UUID.randomUUID();
        UUID bhId = UUID.randomUUID();

        NewBusinessHourDto newBusinessHourDto = new NewBusinessHourDto(
                1,
                LocalTime.of(12, 40),
                LocalTime.of(19, 10),
                restaurantId
        );

        // then
        BusinessHour businessHour = mapper.dtoToBusinessHour(newBusinessHourDto, bhId);

        // test
        assertEquals(bhId, businessHour.getId());
        assertEquals(newBusinessHourDto.dayOfWeek(), businessHour.getDayOfWeek());
        assertEquals(newBusinessHourDto.openingHour(), businessHour.getOpeningHour());
        assertEquals(newBusinessHourDto.closingHour(), businessHour.getClosingHour());
        assertEquals(newBusinessHourDto.restaurantId(), restaurantId);
    }
}
