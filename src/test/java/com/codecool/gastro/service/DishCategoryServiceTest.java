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
    DishCategoryService service;
    @Mock
    DishCategoryMapper mapper;
    @Mock
    DishCategoryRepository repository;

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

    @Captor
    ArgumentCaptor<DishCategory> captor;

    @Test
    void testSaveDishCategory_ShouldSaveWithLowerCaseLettersAndReturnDistCategoryDto_WhenCalled() {
        // when
        when(mapper.dtoToDishCategory(newDishCategoryDto)).thenReturn(dishCategory);
        when(repository.save(dishCategory)).thenReturn(dishCategory);
        when(mapper.toDto(dishCategory)).thenReturn(dishCategoryDto);
        DishCategoryDto exceptedDishCategoryDto = service.saveDishCategory(newDishCategoryDto);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertNotEquals(captor.getValue().getCategory(), exceptedDishCategoryDto.category());
    }

}