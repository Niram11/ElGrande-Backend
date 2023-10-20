package com.codecool.gastro.service;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.PromotedLocalRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.PromotedLocalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotedLocalServiceTest {

    @InjectMocks
    private PromotedLocalService promotedLocalService;

    @Mock
    private PromotedLocalRepository promotedLocalRepository;

    @Mock
    private PromotedLocalMapper promotedLocalMapper;

    private NewPromotedLocalDto newPromotedLocalDto;
    private PromotedLocal promotedLocal;

    @BeforeEach
    void setUp() {
        newPromotedLocalDto = new NewPromotedLocalDto(
                LocalTime.of(10, 0), // Start date
                LocalTime.of(15, 0), // End date
                UUID.randomUUID() // Restaurant ID
        );

        promotedLocal = new PromotedLocal();
        promotedLocal.setId(UUID.randomUUID());
        promotedLocal.setStartDate(LocalTime.of(10, 0));
        promotedLocal.setEndDate(LocalTime.of(15, 0));
    }

    @Test
    void testGetPromotedLocals() {
        // Arrange
        List<PromotedLocal> promotedLocals = new ArrayList<>();
        promotedLocals.add(promotedLocal);

        when(promotedLocalRepository.findAll()).thenReturn(promotedLocals);
        when(promotedLocalMapper.toDto(promotedLocal)).thenReturn(new PromotedLocalDto(
                promotedLocal.getId(),
                promotedLocal.getStartDate(),
                promotedLocal.getEndDate()
        ));

        // Act
        List<PromotedLocalDto> result = promotedLocalService.getPromotedLocals();

        // Assert
        assertEquals(1, result.size());
        PromotedLocalDto dto = result.get(0);
        assertEquals(promotedLocal.getId(), dto.id());
        assertEquals(promotedLocal.getStartDate(), dto.startDate());
        assertEquals(promotedLocal.getEndDate(), dto.endDate());

        verify(promotedLocalRepository, times(1)).findAll();
        verify(promotedLocalMapper, times(1)).toDto(promotedLocal);
    }

    @Test
    void testSaveNewPromotedLocal() {
        // Arrange
        when(promotedLocalMapper.dtoToPromotedLocal(newPromotedLocalDto)).thenReturn(promotedLocal);
        when(promotedLocalRepository.save(promotedLocal)).thenReturn(promotedLocal);
        when(promotedLocalMapper.toDto(promotedLocal)).thenReturn(new PromotedLocalDto(
                promotedLocal.getId(),
                promotedLocal.getStartDate(),
                promotedLocal.getEndDate()
        ));

        // Act
        PromotedLocalDto result = promotedLocalService.saveNewPromotedLocal(newPromotedLocalDto);

        // Assert
        assertEquals(promotedLocal.getId(), result.id());
        assertEquals(promotedLocal.getStartDate(), result.startDate());
        assertEquals(promotedLocal.getEndDate(), result.endDate());

        verify(promotedLocalMapper, times(1)).dtoToPromotedLocal(newPromotedLocalDto);
        verify(promotedLocalRepository, times(1)).save(promotedLocal);
        verify(promotedLocalMapper, times(1)).toDto(promotedLocal);
    }

//    @Test
//    void testUpdatePromotedLocal() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        NewPromotedLocalDto updatedDto = new NewPromotedLocalDto(
//                LocalTime.of(14, 0),
//                LocalTime.of(18, 0),
//                UUID.randomUUID()
//        );
//        Restaurant restaurant = new Restaurant();
//
//        PromotedLocal updatedPromotedLocal = new PromotedLocal();
//        updatedPromotedLocal.setId(id);
//        updatedPromotedLocal.setStartDate(updatedDto.startDate());
//        updatedPromotedLocal.setEndDate(updatedDto.endDate());
//        updatedPromotedLocal.setRestaurant(restaurant);
//
//        when(promotedLocalRepository.findById(restaurant.getId()));
//        when(promotedLocalRepository.save(updatedPromotedLocal)).thenReturn(updatedPromotedLocal);
//        when(promotedLocalRepository.findById(id)).thenReturn(Optional.of(promotedLocal));
//        when(promotedLocalMapper.dtoToPromotedLocal(id, updatedDto)).thenReturn(updatedPromotedLocal);
//        when(promotedLocalMapper.toDto(updatedPromotedLocal)).thenReturn(new PromotedLocalDto(
//                updatedPromotedLocal.getId(),
//                updatedPromotedLocal.getStartDate(),
//                updatedPromotedLocal.getEndDate()
//        ));
//
//        // Act
//        PromotedLocalDto result = promotedLocalService.updatePromotedLocal(id, updatedDto);
//
//        // Assert
//        assertEquals(updatedPromotedLocal.getId(), result.id());
//        assertEquals(updatedPromotedLocal.getStartDate(), result.startDate());
//        assertEquals(updatedPromotedLocal.getEndDate(), result.endDate());
//
//        verify(promotedLocalRepository, times(1)).findById(id);
//        verify(promotedLocalRepository, times(1)).save(updatedPromotedLocal);
//        verify(promotedLocalMapper, times(1)).dtoToPromotedLocal(id, updatedDto);
//        verify(promotedLocalMapper, times(1)).toDto(updatedPromotedLocal);
//    }

//    @Test
//    void testDeletePromotedLocalWithValidId() {
//        // Arrange
//        UUID id = UUID.randomUUID();
//        when(promotedLocalRepository.existsById(id)).thenReturn(true);
//
//        // Act
//        promotedLocalService.deletePromotedLocal(id);
//
//        // Assert
//        verify(promotedLocalRepository, times(1)).deleteById(id);
//    }
}