package com.codecool.gastro.service;

import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishCategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishCategoryServiceTest {

    @InjectMocks
    private DishCategoryService service;
    @Mock
    private DishCategoryMapper mapper;
    @Mock
    private DishCategoryRepository repository;

    private UUID dishCategoryId;

    private DishCategory dishCategory;
    private DishCategoryDto dishCategoryDto;

    private NewDishCategoryDto newDishCategoryDto;

    @BeforeEach
    void setUp() {
        dishCategoryId = UUID.randomUUID();

        dishCategory = new DishCategory();
        dishCategory.setId(dishCategoryId);
        dishCategory.setCategory("Category");

        dishCategoryDto = new DishCategoryDto(
                dishCategoryId,
                "Category"
        );

        newDishCategoryDto = new NewDishCategoryDto(
                "Category"
        );
    }

    @Test
    void testGetAllDishCategories_ShouldReturnList_WhenCalled() {
        // when
        when(repository.findAll()).thenReturn(List.of());
        List<DishCategoryDto> list = service.getAllDishCategories();

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testGetDishCategoryById_ShouldReturnDishCategory_WhenExist() {
        // when
        when(repository.findById(dishCategoryId)).thenReturn(Optional.of(dishCategory));
        when(mapper.toDto(dishCategory)).thenReturn(dishCategoryDto);
        DishCategoryDto expectedDishCategoryDto = service.getDishCategoryById(dishCategoryId);

        // then
        assertEquals(expectedDishCategoryDto.id(), dishCategoryId);
        assertEquals(expectedDishCategoryDto.category(), dishCategoryDto.category());
    }

    @Test
    void testGetDishCategoryById_ShouldThrowObjectNotFoundException_WhenNoDishCategory() {
        // when
        doThrow(ObjectNotFoundException.class).when(repository).findById(dishCategoryId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.getDishCategoryById(dishCategoryId));
    }

    @Captor
    ArgumentCaptor<DishCategory> captor;

    @Test
    void testSaveDishCategory_ShouldSaveWithLowerCaseLettersAndReturnDistCategoryDto_WhenCalled() {
        // when
        when(mapper.dtoToDishCategory(newDishCategoryDto)).thenReturn(dishCategory);
        when(repository.save(dishCategory)).thenReturn(dishCategory);
        when(mapper.toDto(dishCategory)).thenReturn(dishCategoryDto);
        DishCategoryDto exceptedDishCategoryDto = service.saveDishCategory(newDishCategoryDto);

        // test
        verify(repository, times(1)).save(captor.capture());
        assertNotEquals(captor.getValue().getCategory(), exceptedDishCategoryDto.category());
    }

    @Test
    void testUpdateDishCategory_ShouldSaveWithLowerCaseLettersAndReturnDistCategoryDto_WhenCalled() {
        // when
        when(mapper.dtoToDishCategory(dishCategoryId, newDishCategoryDto)).thenReturn(dishCategory);
        when(repository.save(dishCategory)).thenReturn(dishCategory);
        when(mapper.toDto(dishCategory)).thenReturn(dishCategoryDto);
        DishCategoryDto exceptedDishCategoryDto = service.updateDishCategory(dishCategoryId, newDishCategoryDto);

        // test
        verify(repository, times(1)).save(captor.capture());
        assertNotEquals(captor.getValue().getCategory(), exceptedDishCategoryDto.category());
    }
}