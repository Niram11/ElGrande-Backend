package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.service.validation.BusinessHourValidation;
import jakarta.transaction.TransactionalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.transform.TransformerException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessHoursServiceTest {
    @InjectMocks
    BusinessHourService service;
    @Mock
    BusinessHourRepository repository;
    @Mock
    BusinessHourMapper mapper;
    @Mock
    BusinessHourValidation validation;

    private UUID businessHourId;
    private UUID restaurantId;
    private BusinessHourDto businessHourDto;
    private NewBusinessHourDto newBusinessHourDto;
    private BusinessHour businessHour;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        businessHourId = UUID.fromString("62cb7e9e-c7c9-48d5-9b64-c9d399bb6790");
        restaurantId = UUID.fromString("7d4733cb-f739-4dcc-bce1-40ef0f5cc073");

        businessHourDto = new BusinessHourDto(
                businessHourId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50),
                false
        );

        newBusinessHourDto = new NewBusinessHourDto(
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50)
        );

        businessHour = new BusinessHour();

        restaurant = new Restaurant();
        restaurant.setId(restaurantId);
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnListOfBusinessHourDto_WhenExist() {
        // given
        BusinessHour businessHourOne = businessHour;
        BusinessHour businessHourTwo = new BusinessHour();

        BusinessHourDto businessHourDtoOne = businessHourDto;
        BusinessHourDto businessHourDtoTwo = new BusinessHourDto(
                businessHourId,
                2,
                LocalTime.of(13, 30),
                LocalTime.of(16, 50),
                false
        );

        // when
        when(repository.findAllByRestaurantId(restaurantId)).thenReturn(List.of(businessHourOne, businessHourTwo));
        when(mapper.toDto(businessHourOne)).thenReturn(businessHourDtoOne);
        when(mapper.toDto(businessHourTwo)).thenReturn(businessHourDtoTwo);
        List<BusinessHourDto> list = service.getBusinessHoursByRestaurantId(restaurantId);

        // then
        assertEquals(2, list.size());
        verify(repository, times(1)).findAllByRestaurantId(restaurantId);
        verify(mapper, times(1)).toDto(businessHourOne);
        verify(mapper, times(1)).toDto(businessHourTwo);
    }

    @Test
    void testGetBusinessHourByRestaurantId_ShouldReturnEmptyList_WhenNoBusinessHours() {
        // when
        when(repository.findAllByRestaurantId(restaurantId)).thenReturn(List.of());
        List<BusinessHourDto> list = service.getBusinessHoursByRestaurantId(restaurantId);

        // then
        assertEquals(0, list.size());
        verify(repository, times(1)).findAllByRestaurantId(restaurantId);
    }

    @Test
    void testUpdateBusinessHour_ShouldReturnBusinessHoursDto_WhenCalledWithValidData() {
        // given
        businessHour.setId(businessHourId);
        businessHour.setDayOfWeek(newBusinessHourDto.dayOfWeek());
        businessHour.setOpeningHour(newBusinessHourDto.openingHour());
        businessHour.setClosingHour(newBusinessHourDto.closingHour());
        businessHour.setRestaurant(restaurant);

        // when
        when(validation.validateEntityById(businessHourId)).thenReturn(businessHour);
        when(repository.save(businessHour)).thenReturn(businessHour);
        when(mapper.toDto(businessHour)).thenReturn(businessHourDto);
        BusinessHourDto updatedBusinessHourDto = service.updateBusinessHour(businessHourId, newBusinessHourDto);

        // then
        assertEquals(updatedBusinessHourDto, businessHourDto);
        verify(repository, times(1)).save(businessHour);
        verify(mapper, times(1)).toDto(businessHour);
    }

    @Test
    void testUpdateBusinessHour_ShouldThrowObjectNotFoundException_WhenNoBusinessHour() {
        // when
        doThrow(ObjectNotFoundException.class).when(validation).validateEntityById(businessHourId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.updateBusinessHour(businessHourId, newBusinessHourDto));
    }
}

