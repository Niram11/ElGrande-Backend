package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
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
    private BusinessHourService service;

    @Mock
    private BusinessHourRepository repository;

    @Mock
    private BusinessHourMapper mapper;


    private UUID businessHourId;
    private UUID restaurantId;
    private BusinessHourDto businessHourDto;
    private NewBusinessHourDto newBusinessHourDto;
    private BusinessHour businessHour;

    @BeforeEach
    void setUp() {
        businessHourId = UUID.fromString("62cb7e9e-c7c9-48d5-9b64-c9d399bb6790");
        restaurantId = UUID.fromString("7d4733cb-f739-4dcc-bce1-40ef0f5cc073");

        businessHourDto = new BusinessHourDto(
                businessHourId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50)
        );

        newBusinessHourDto = new NewBusinessHourDto(
                1,
                LocalTime.of(12, 30),
                LocalTime.of(15, 50),
                restaurantId
        );

        businessHour = new BusinessHour();
    }

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
    void testGetBusinessHourById_ShouldReturnBusinessHourDto_WhenExist() {

        // when
        when(repository.findById(businessHourId)).thenReturn(Optional.of(businessHour));
        when(mapper.toDto(businessHour)).thenReturn(businessHourDto);

        // then
        BusinessHourDto businessHourDtoFromDB = service.getBusinessHourById(businessHourId);

        // test
        assertEquals(businessHourDto, businessHourDtoFromDB);
        verify(repository, times(1)).findById(businessHourId);
        verify(mapper, times(1)).toDto(businessHour);
    }

    @Test
    void testGetBusinessHourById_ShouldThrowObjectNotFoundException_WhenNoBusinessHours() {
        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getBusinessHourById(businessHourId));
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnListOfBusinessHourDto_WhenExist() {
        BusinessHour businessHourOne = businessHour;
        BusinessHour businessHourTwo = new BusinessHour();

        BusinessHourDto businessHourDtoOne = businessHourDto;
        BusinessHourDto businessHourDtoTwo = new BusinessHourDto(
                businessHourId,
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
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        businessHour.setId(businessHourId);
        businessHour.setDayOfWeek(newBusinessHourDto.dayOfWeek());
        businessHour.setOpeningHour(newBusinessHourDto.openingHour());
        businessHour.setClosingHour(newBusinessHourDto.closingHour());
        businessHour.setRestaurant(restaurant);

        // when
        when(repository.save(businessHour)).thenReturn(businessHour);
        when(mapper.dtoToBusinessHour(newBusinessHourDto, businessHourId)).thenReturn(businessHour);
        when(mapper.dtoToBusinessHour(newBusinessHourDto)).thenReturn(businessHour);
        when(mapper.toDto(businessHour)).thenReturn(businessHourDto);

        // then
        BusinessHourDto savedBusinessHourDto = service.saveNewBusinessHour(newBusinessHourDto);
        BusinessHourDto updatedBusinessHourDto = service.updateBusinessHour(businessHourId, newBusinessHourDto);

        // test
        assertEquals(savedBusinessHourDto, businessHourDto);
        assertEquals(updatedBusinessHourDto, businessHourDto);
        verify(mapper, times(1)).dtoToBusinessHour(newBusinessHourDto);
        verify(mapper, times(1)).dtoToBusinessHour(newBusinessHourDto, businessHourId);
        verify(repository, times(2)).save(businessHour);
        verify(mapper, times(2)).toDto(businessHour);
    }

    @Test
    void testDeleteBusinessHour_ShouldDeleteBusinessHour_WhenCalled() {
        // given
        businessHour.setId(businessHourId);

        // when
        when(mapper.dtoToBusinessHour(businessHourId)).thenReturn(businessHour);

        // then
        service.deleteBusinessHour(businessHourId);

        // test
        verify(mapper, times(1)).dtoToBusinessHour(businessHourId);
        verify(repository, times(1)).delete(businessHour);
    }

    @Test
    void testSaveMultipleNewBusinessHour_ShouldReturnListOfBusinessHours_WhenAllAreValid() {
        // given
        List<NewBusinessHourDto> list = List.of(newBusinessHourDto, newBusinessHourDto);

        // then
        List<BusinessHourDto> testedList = service.saveMultipleNewBusinessHour(list);

        // test
        assertEquals(2, testedList.size());
    }

    @Test
    void testSaveMultipleNewBusinessHour_ShouldRollback_WhenOneIsInvalid() {
        // given
        List<NewBusinessHourDto> list = List.of(newBusinessHourDto, newBusinessHourDto);

        // when
        when(mapper.dtoToBusinessHour(newBusinessHourDto)).thenReturn(businessHour);
        when(repository.save(businessHour)).thenThrow(TransactionalException.class);

        // test
        assertThrows(TransactionalException.class, () -> service.saveMultipleNewBusinessHour(list));
        verify(mapper, times(1)).dtoToBusinessHour(newBusinessHourDto);
        verify(repository, times(1)).save(businessHour);

    }
}

