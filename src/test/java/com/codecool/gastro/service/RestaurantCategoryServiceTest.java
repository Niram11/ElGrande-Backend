package com.codecool.gastro.service;

import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.repository.RestaurantCategoryRepository;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import com.codecool.gastro.service.mapper.RestaurantCategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantCategoryServiceTest {


    @InjectMocks
    private RestaurantCategoryService service;

    @Mock
    private RestaurantCategoryRepository repository;

    @Mock
    private RestaurantCategoryMapper mapper;

    private UUID categoryId;
    private RestaurantCategoryDto categoryDto;
    private RestaurantCategory category;

    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();

        categoryDto = new RestaurantCategoryDto(categoryId, "Italian");
        category = new RestaurantCategory();
        category.setId(categoryId);
    }

    @Test
    void testGetRestaurantCategories_ShouldReturnListOfRestaurantCategoryDto_WhenCalled() {
        // given
        List<RestaurantCategory> categories = List.of(category);
        when(repository.findAll()).thenReturn(categories);
        when(mapper.toDto(category)).thenReturn(categoryDto);

        // test
        List<RestaurantCategoryDto> result = service.getRestaurantCategories();

        // assert
        assertEquals(1, result.size());
        assertEquals(categoryDto, result.get(0));
    }

    @Test
    void testSaveRestaurantCategory_ShouldReturnRestaurantCategoryDto_WhenSaved() {
        // given
        NewRestaurantCategoryDto newCategoryDto = new NewRestaurantCategoryDto("Italian");
        when(mapper.dtoToRestaurantCategory(newCategoryDto)).thenReturn(category);
        when(repository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(categoryDto);

        // test
        RestaurantCategoryDto result = service.saveRestaurantCategory(newCategoryDto);

        // assert
        assertEquals(categoryDto, result);
    }



    @Test
    void testDeleteRestaurantCategory_ShouldCallRepositoryDelete_WhenCalled() {
        // given
        when(mapper.dtoToRestaurantCategory(categoryId)).thenReturn(category);

        // test
        service.deleteRestaurantCategory(categoryId);

        // verify
        verify(repository, times(1)).delete(category);
    }
}

