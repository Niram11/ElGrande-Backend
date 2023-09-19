package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


    @Test
    void testGetBusinessHours_ShouldReturnEmptyList_WhenNoBusinessHour() {
        // when
        when(repository.findAll()).thenReturn(List.of());

        // then
        List<BusinessHourDto> list = service.getBusinessHours();

        // test
        assertEquals(0, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetBusinessHoursById_ShouldReturnBusinessHourDto_WhenExist() {
        // given
        Optional<BusinessHour> businessHour = Optional.of(new BusinessHour());
        UUID bhId = UUID.randomUUID();
        BusinessHourDto businessHourDto = new BusinessHourDto(
                bhId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50)
        );

        // when
        when(repository.findById(bhId)).thenReturn(businessHour);
        when(mapper.toDto(businessHour.get())).thenReturn(businessHourDto);

        // then
        BusinessHourDto businessHourDtoFromDB = service.getBusinessHourById(bhId);

        // test
        assertEquals(businessHourDto, businessHourDtoFromDB);
        verify(repository, times(1)).findById(bhId);
        verify(mapper, times(1)).toDto(businessHour.get());
    }

    @Test
    void testGetBusinessHourById_ShouldThrowObjectNotFoundException_WhenNoBusinessHours() {
        // given
        UUID bhId = UUID.randomUUID();

        // when
        assertThrows(ObjectNotFoundException.class, () -> service.getBusinessHourById(bhId));
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnListOfBusinessHourDto_WhenExist() {
        // given
        UUID restaurantId = UUID.randomUUID();
        UUID bhId = UUID.randomUUID();

        BusinessHour businessHourOne = new BusinessHour();
        BusinessHour businessHourTwo = new BusinessHour();

        BusinessHourDto businessHourDtoOne = new BusinessHourDto(
                bhId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50)
        );

        BusinessHourDto businessHourDtoTwo = new BusinessHourDto(
                bhId,
                2,
                LocalTime.of(13, 30),
                LocalTime.of(16, 50)
        );

        // when
        when(repository.findAllByRestaurantId(restaurantId)).thenReturn(List.of(businessHourOne, businessHourTwo));
        when(mapper.toDto(businessHourOne)).thenReturn(businessHourDtoOne);
        when(mapper.toDto(businessHourTwo)).thenReturn(businessHourDtoTwo);

        // then
        List<BusinessHourDto> list = service.getBusinessHoursByRestaurantId(restaurantId);

        // test
        assertEquals(2, list.size());
        verify(repository, times(1)).findAllByRestaurantId(restaurantId);
        verify(mapper, times(1)).toDto(businessHourOne);
        verify(mapper, times(1)).toDto(businessHourTwo);
    }

    @Test
    void testGetBusinessHourByRestaurantId_ShouldReturnEmptyList_WhenNoBusinessHours() {
        // given
        UUID restaurantId = UUID.randomUUID();

        // when
        when(repository.findAllByRestaurantId(restaurantId)).thenReturn(List.of());

        // then
        List<BusinessHourDto> list = service.getBusinessHoursByRestaurantId(restaurantId);

        // test
        assertEquals(0, list.size());
        verify(repository, times(1)).findAllByRestaurantId(restaurantId);
    }

    @Test
    void testSaveNewBusinessHoursAndUpdate_ShouldReturnBusinessHoursDto_WhenCalledWithValidData() {
        // given
        UUID bhId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        NewBusinessHourDto newBusinessHourDto = new NewBusinessHourDto(
                1,
                LocalTime.of(12, 30),
                LocalTime.of(16, 50),
                restaurantId
        );

        BusinessHourDto businessHourDto = new BusinessHourDto(
                bhId,
                newBusinessHourDto.dayOfWeek(),
                newBusinessHourDto.openingHour(),
                newBusinessHourDto.closingHour()
        );

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        BusinessHour businessHour = new BusinessHour();
        businessHour.setId(bhId);
        businessHour.setDayOfWeek(newBusinessHourDto.dayOfWeek());
        businessHour.setOpeningHour(newBusinessHourDto.openingHour());
        businessHour.setClosingHour(newBusinessHourDto.closingHour());
        businessHour.setRestaurant(restaurant);

        // when
        when(repository.save(businessHour)).thenReturn(businessHour);
        when(mapper.dtoToBusinessHour(newBusinessHourDto, bhId)).thenReturn(businessHour);
        when(mapper.dtoToBusinessHour(newBusinessHourDto)).thenReturn(businessHour);
        when(mapper.toDto(businessHour)).thenReturn(businessHourDto);

        // then
        BusinessHourDto savedBusinessHourDto = service.saveNewBusinessHour(newBusinessHourDto);
        BusinessHourDto updatedBusinessHourDto = service.updateBusinessHour(bhId, newBusinessHourDto);

        // test
        assertEquals(savedBusinessHourDto, businessHourDto);
        assertEquals(updatedBusinessHourDto, businessHourDto);
        verify(mapper, times(1)).dtoToBusinessHour(newBusinessHourDto);
        verify(mapper, times(1)).dtoToBusinessHour(newBusinessHourDto, bhId);
        verify(repository, times(2)).save(businessHour);
        verify(mapper, times(2)).toDto(businessHour);
    }

    @Test
    void testDeleteBusinessHour_ShouldDeleteBusinessHour_WhenCalled() {
        // given
        UUID bhId = UUID.randomUUID();

        BusinessHour businessHour = new BusinessHour();
        businessHour.setId(bhId);

        // when
        when(mapper.dtoToBusinessHour(bhId)).thenReturn(businessHour);

        // then
        service.deleteBusinessHour(bhId);

        // test
        verify(mapper, times(1)).dtoToBusinessHour(bhId);
        verify(repository, times(1)).delete(businessHour);
    }
}

