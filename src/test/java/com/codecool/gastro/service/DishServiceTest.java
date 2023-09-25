package com.codecool.gastro.service;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishCategoryMapper;
import com.codecool.gastro.service.mapper.DishMapper;
import com.codecool.gastro.service.mapper.IngredientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @InjectMocks
    private DishService service;
    @Mock
    private DishRepository repository;
    @Mock
    private DishMapper mapper;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientMapper ingredientMapper;
    @Mock
    private DishCategoryRepository dishCategoryRepository;
    @Mock
    private DishCategoryMapper dishCategoryMapper;

    private UUID restaurantId;
    private UUID dishId;
    private Dish dish;
    private DishDto dishDto;
    private NewDishDto newDishDto;

    @BeforeEach
    void setUp() {
        restaurantId = UUID.randomUUID();
        dishId = UUID.randomUUID();

        dish = new Dish();

        dishDto = new DishDto(
                dishId,
                "Pizza",
                BigDecimal.valueOf(15),
                Set.of(),
                Set.of()
        );


    }

    @Test
    void testGetAllDishes_ShouldReturnList_WhenCalled() {
        // when
        when(repository.findAll()).thenReturn(List.of());
        List<DishDto> list = service.getAllDishes();

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testGetDishesByRestaurant_ShouldReturnList_WhenCalled() {
        // when
        when(repository.findByRestaurant(restaurantId)).thenReturn(List.of());
        List<DishDto> list = service.getDishesByRestaurant(restaurantId);

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testGetDishById_ShouldReturnDishDto_WhenExist() {
        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(mapper.toDto(dish)).thenReturn(dishDto);
        DishDto expectedDishDto = service.getDishById(dishId);

        // then
        assertEquals(expectedDishDto.id(), dishDto.id());
        assertEquals(expectedDishDto.dishName(), dishDto.dishName());
        assertEquals(expectedDishDto.price(), dishDto.price());
        assertEquals(expectedDishDto.ingredients().size(), dishDto.ingredients().size());
        assertEquals(expectedDishDto.categories().size(), dishDto.categories().size());
    }

    @Test
    void testGetDishById_ShouldThrowObjectNotFoundException_WhenNoDish() {
        // when
        doThrow(ObjectNotFoundException.class).when(repository).findById(dishId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.getDishById(dishId));
    }

    @Test
    void testSaveNewDish_ShouldReturnDishDto_WhenCalled() {
        // when
        when(mapper.dtoToDish(newDishDto)).thenReturn(dish);
        when(repository.save(dish)).thenReturn(dish);
        when(mapper.toDto(dish)).thenReturn(dishDto);
        DishDto expectedDishDto = service.saveNewDish(newDishDto);

        // then
        assertEquals(expectedDishDto.dishName(), dishDto.dishName());
        assertEquals(expectedDishDto.price(), dishDto.price());
        assertEquals(expectedDishDto.ingredients().size(), dishDto.ingredients().size());
        assertEquals(expectedDishDto.categories().size(), dishDto.categories().size());
    }

    @Test
    void testUpdateDish_ShouldReturnDishDto_WhenCalled() {
        // when
        when(mapper.dtoToDish(dishId, newDishDto)).thenReturn(dish);
        when(repository.save(dish)).thenReturn(dish);
        when(mapper.toDto(dish)).thenReturn(dishDto);
        DishDto expectedDishDto = service.updateDish(dishId, newDishDto);

        // then
        assertEquals(expectedDishDto.id(), dishDto.id());
        assertEquals(expectedDishDto.dishName(), dishDto.dishName());
        assertEquals(expectedDishDto.price(), dishDto.price());
        assertEquals(expectedDishDto.ingredients().size(), dishDto.ingredients().size());
        assertEquals(expectedDishDto.categories().size(), dishDto.categories().size());
    }

    @Test
    void testAssignIngredientToDish_ShouldThrowObjectNotFoundException_WhenNoDish() {
        // when
        doThrow(ObjectNotFoundException.class).when(repository).findById(dishId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.assignIngredientToDish(dishId, Set.of()));
    }

    @Captor
    ArgumentCaptor<Dish> captor;

    @Test
    void testAssignIngredientToDish_ShouldAppendIngredientsToDish_WhenCalled() {
        // given
        NewIngredientDto newIngredientDtoOne = new NewIngredientDto("Pasta");
        NewIngredientDto newIngredientDtoTwo = new NewIngredientDto("Tomato");

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("Pasta");

        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("Tomato");

        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findByName(newIngredientDtoOne.name())).thenReturn(Optional.of(ingredientOne));
        when(ingredientRepository.findByName(newIngredientDtoTwo.name())).thenReturn(Optional.of(ingredientTwo));

        service.assignIngredientToDish(dishId, ingredientDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getIngredients().size(), 2);
    }

    @Test
    void testAssignIngredientToDish_ShouldAppendOnlyIngredients_WhenNoAlreadyThere() {
        // given
        NewIngredientDto newIngredientDtoOne = new NewIngredientDto("Pasta");
        NewIngredientDto newIngredientDtoTwo = new NewIngredientDto("Tomato");
        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("Pasta");
        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("Tomato");
        dish.assignIngredient(ingredientOne);

        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findByName(newIngredientDtoOne.name())).thenReturn(Optional.of(ingredientOne));
        when(ingredientRepository.findByName(newIngredientDtoTwo.name())).thenReturn(Optional.of(ingredientTwo));

        service.assignIngredientToDish(dishId, ingredientDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getIngredients().size(), 2);
    }

    @Test
    void testAssignIngredientToDish_ShouldSaveAndAppendIngredients_WhenNoIngredients() {
        // given
        NewIngredientDto newIngredientDtoOne = new NewIngredientDto("Pasta");
        NewIngredientDto newIngredientDtoTwo = new NewIngredientDto("Tomato");

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("Pasta");

        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("Tomato");

        dish.assignIngredient(ingredientOne);
        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(ingredientMapper.dtoToIngredient(newIngredientDtoOne)).thenReturn(ingredientOne);
        when(ingredientMapper.dtoToIngredient(newIngredientDtoTwo)).thenReturn(ingredientTwo);

        service.assignIngredientToDish(dishId, ingredientDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getIngredients().size(), 2);
    }

    @Test
    void testAssignDishCategoryToDish_ShouldAppendDishCategoryToDish_WhenCalled() {
        // given
        NewDishCategoryDto newDishCategoryDtoOne = new NewDishCategoryDto("Pizza");
        NewDishCategoryDto newDishCategoryDtoTwo = new NewDishCategoryDto("Pasta");

        DishCategory dishCategoryOne = new DishCategory();
        dishCategoryOne.setCategory("Pizza");

        DishCategory dishCategoryTwo = new DishCategory();
        dishCategoryTwo.setCategory("Pasta");

        Set<NewDishCategoryDto> newDishCategoryDtoSet = Set.of(newDishCategoryDtoOne, newDishCategoryDtoTwo);

        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoOne.category())).thenReturn(Optional.of(dishCategoryOne));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoTwo.category())).thenReturn(Optional.of(dishCategoryTwo));

        service.assignDishCategoryToDish(dishId, newDishCategoryDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getCategories().size(), 2);
    }

    @Test
    void testAssignDishCategoryToDish_ShouldAppendOnlyDishCategory_WhenNoAlreadyThere() {
        // given
        NewDishCategoryDto newDishCategoryDtoOne = new NewDishCategoryDto("Pizza");
        NewDishCategoryDto newDishCategoryDtoTwo = new NewDishCategoryDto("Pasta");

        DishCategory dishCategoryOne = new DishCategory();
        dishCategoryOne.setCategory("Pizza");

        DishCategory dishCategoryTwo = new DishCategory();
        dishCategoryTwo.setCategory("Pasta");

        Set<NewDishCategoryDto> newDishCategoryDtoSet = Set.of(newDishCategoryDtoOne, newDishCategoryDtoTwo);
        dish.assignCategories(dishCategoryOne);


        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoOne.category())).thenReturn(Optional.of(dishCategoryOne));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoTwo.category())).thenReturn(Optional.of(dishCategoryTwo));

        service.assignDishCategoryToDish(dishId, newDishCategoryDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getCategories().size(), 2);
    }

    @Test
    void testAssignDishCategoryToDish_ShouldSaveAndAppendDishCategory_WhenNoDishCategory() {
        // given
        NewDishCategoryDto newDishCategoryDtoOne = new NewDishCategoryDto("Pizza");
        NewDishCategoryDto newDishCategoryDtoTwo = new NewDishCategoryDto("Pasta");

        DishCategory dishCategoryOne = new DishCategory();
        dishCategoryOne.setCategory("Pizza");

        DishCategory dishCategoryTwo = new DishCategory();
        dishCategoryTwo.setCategory("Pasta");

        Set<NewDishCategoryDto> newDishCategoryDtoSet = Set.of(newDishCategoryDtoOne, newDishCategoryDtoTwo);
        dish.assignCategories(dishCategoryOne);

        // when
        when(repository.findById(dishId)).thenReturn(Optional.of(dish));
        when(dishCategoryRepository.findByCategory(anyString())).thenReturn(Optional.empty());
        when(dishCategoryMapper.dtoToDishCategory(newDishCategoryDtoOne)).thenReturn(dishCategoryOne);
        when(dishCategoryMapper.dtoToDishCategory(newDishCategoryDtoTwo)).thenReturn(dishCategoryTwo);

        service.assignDishCategoryToDish(dishId, newDishCategoryDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getCategories().size(), 2);
    }
}