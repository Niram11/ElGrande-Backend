package com.codecool.gastro.service.mapper;
import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotedLocalMapperTest {
    private final PromotedLocalMapper mapper = Mappers.getMapper(PromotedLocalMapper.class);
    private final static UUID PROMOTED_LOCAL_ID = UUID.randomUUID();
    private final static UUID RESTAURANT_ID = UUID.randomUUID();
    private final static LocalTime START_TIME = LocalTime.of(10, 0);
    private final static LocalTime END_TIME = LocalTime.of(18, 0);

    @Test
    void testMappingPromotedLocalToDtoShouldMapPromotedLocalToDtoWhenProvidingValidData() {
        // Given
        PromotedLocal promotedLocal = new PromotedLocal();
        promotedLocal.setId(PROMOTED_LOCAL_ID);
        promotedLocal.setRestaurant(new Restaurant());
        promotedLocal.getRestaurant().setId(RESTAURANT_ID);
        promotedLocal.setStartDate(START_TIME);
        promotedLocal.setEndDate(END_TIME);

        // When
        PromotedLocalDto promotedLocalDto = mapper.toDto(promotedLocal);

        // Then
        assertEquals(promotedLocalDto.id(), PROMOTED_LOCAL_ID);
        assertEquals(promotedLocalDto.startDate(), START_TIME);
        assertEquals(promotedLocalDto.endDate(), END_TIME);
    }

    @Test
    void testMappingDtoToPromotedLocalShouldMapDtoToPromotedLocalWhenProvidingValidData() {
        // Given
        NewPromotedLocalDto newPromotedLocalDto = new NewPromotedLocalDto(START_TIME, END_TIME, RESTAURANT_ID);

        // When
        PromotedLocal promotedLocal = mapper.dtoToPromotedLocal(newPromotedLocalDto);

        // Then
        assertEquals(promotedLocal.getStartDate(), newPromotedLocalDto.startDate());
        assertEquals(promotedLocal.getEndDate(), newPromotedLocalDto.endDate());
        assertEquals(promotedLocal.getRestaurant().getId(), newPromotedLocalDto.restaurantId());
    }

    @Test
    void testMappingDtoToPromotedLocalWithIdShouldMapDtoToPromotedLocalWithIdWhenProvidingValidData() {
        // When
        PromotedLocal promotedLocal = mapper.dtoToPromotedLocal(PROMOTED_LOCAL_ID, new NewPromotedLocalDto(START_TIME, END_TIME, RESTAURANT_ID));

        // Then
        assertEquals(promotedLocal.getId(), PROMOTED_LOCAL_ID);
        assertEquals(promotedLocal.getStartDate(), START_TIME);
        assertEquals(promotedLocal.getEndDate(), END_TIME);
        assertEquals(promotedLocal.getRestaurant().getId(), RESTAURANT_ID);
    }
}
