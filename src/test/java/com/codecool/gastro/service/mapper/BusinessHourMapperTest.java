package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessHourMapperTest {
    BusinessHourMapper mapper = Mappers.getMapper(BusinessHourMapper.class);

    private BusinessHour businessHour;
    private UUID bhId;
    private NewBusinessHourDto newBusinessHourDto;
    @BeforeEach
    void setUp() {
        bhId = UUID.randomUUID();

        businessHour = new BusinessHour();
        businessHour.setId(bhId);
        businessHour.setDayOfWeek(1);
        businessHour.setOpeningHour(LocalTime.of(12, 30));
        businessHour.setClosingHour(LocalTime.of(15, 50));

        newBusinessHourDto = new NewBusinessHourDto(
                1,
                LocalTime.of(12, 40),
                LocalTime.of(19, 10)
        );
    }

    @Test
    void testToDto_ShouldReturnMappedBusinessHour_WhenCalled() {
        // when
        BusinessHourDto businessHourDto = mapper.toDto(businessHour);

        // then
        assertEquals(businessHourDto.id(), businessHour.getId());
        assertEquals(businessHourDto.dayOfWeek(), businessHour.getDayOfWeek());
        assertEquals(businessHourDto.openingHour(), businessHour.getOpeningHour());
        assertEquals(businessHourDto.closingHour(), businessHour.getClosingHour());
    }

    @Test
    void testUpdateBusinessHourFromDto_ShouldMapOnlyFieldsThatWereChangedRestShouldBeSame_WhenCalled() {
        // when
        mapper.updateBusinessHourFromDto(newBusinessHourDto, businessHour);

        // then
        assertEquals(bhId, businessHour.getId());
        assertEquals(newBusinessHourDto.dayOfWeek(), businessHour.getDayOfWeek());
        assertEquals(newBusinessHourDto.openingHour(), businessHour.getOpeningHour());
        assertEquals(newBusinessHourDto.closingHour(), businessHour.getClosingHour());
    }

    @Test
    void testDtoToBusinessHours_ShouldReturnMappedBusinessHours_WhenProvidingId() {
        // when
        BusinessHour businessHour = mapper.dtoToBusinessHour(bhId);

        // then
        assertEquals(bhId, businessHour.getId());
    }

    @Test
    void testDtoToBusinessHours_ShouldReturnMappedBusinessHours_WhenProvidingDto() {
        // when
        BusinessHour businessHour = mapper.dtoToBusinessHour(newBusinessHourDto);

        // then
        assertEquals(newBusinessHourDto.dayOfWeek(), businessHour.getDayOfWeek());
        assertEquals(newBusinessHourDto.openingHour(), businessHour.getOpeningHour());
        assertEquals(newBusinessHourDto.closingHour(), businessHour.getClosingHour());
    }

}
