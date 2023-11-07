package com.codecool.gastro.service;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.dto.dishcategory.DishCategoryDto;
import com.codecool.gastro.dto.dishcategory.NewDishCategoryDto;
import com.codecool.gastro.dto.ingredient.IngredientDto;
import com.codecool.gastro.dto.ingredient.NewIngredientDto;
import com.codecool.gastro.repository.DishCategoryRepository;
import com.codecool.gastro.repository.DishRepository;
import com.codecool.gastro.repository.IngredientRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ingredient;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.DishMapper;
import com.codecool.gastro.service.validation.DishValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @InjectMocks
    DishService service;
    @Mock
    DishRepository repository;
    @Mock
    DishMapper mapper;
    @Mock
    DishValidation validation;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    IngredientService ingredientService;
    @Mock
    DishCategoryRepository dishCategoryRepository;
    @Mock
    DishCategoryService dishCategoryService;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    RestaurantValidation restaurantValidation;

    private UUID restaurantId;
    private UUID dishId;
    private Dish dish;
    private DishDto dishDto;
    private NewDishDto newDishDto;
    private EditDishDto editDishDto;

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

        newDishDto = new NewDishDto(
                "Name",
                BigDecimal.valueOf(12),
                restaurantId
        );

        editDishDto = new EditDishDto(
                "Name",
                BigDecimal.valueOf(12)
        );

    }

    @Test
    void testGetDishesByRestaurantId_ShouldReturnList_WhenCalled() {
        // when
        when(repository.findByRestaurantId(restaurantId)).thenReturn(List.of());
        List<DishDto> list = service.getDishesByRestaurantId(restaurantId);

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testGetDishById_ShouldReturnDishDto_WhenExist() {
        // when
        when(validation.validateEntityById(dishId)).thenReturn(dish);
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
        doThrow(ObjectNotFoundException.class).when(validation).validateEntityById(dishId);

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
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(repository.save(dish)).thenReturn(dish);
        when(mapper.toDto(dish)).thenReturn(dishDto);
        DishDto expectedDishDto = service.updateDish(dishId, editDishDto);

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
        doThrow(ObjectNotFoundException.class).when(validation).validateEntityById(dishId);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.assignIngredientToDish(dishId, Set.of()));
    }

    @Captor
    ArgumentCaptor<Dish> captor;

    @Test
    void testAssignIngredientToDish_ShouldAppendIngredientsToDish_WhenCalled() {
        // given
        NewIngredientDto newIngredientDtoOne = new NewIngredientDto("pasta");
        NewIngredientDto newIngredientDtoTwo = new NewIngredientDto("tomato");

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("Pasta");

        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("Tomato");

        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(validation.validateEntityById(dishId)).thenReturn(dish);
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
        NewIngredientDto newIngredientDtoOne = new NewIngredientDto("pasta");
        NewIngredientDto newIngredientDtoTwo = new NewIngredientDto("tomato");

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("pasta");

        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("tomato");

        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(ingredientRepository.findByName(newIngredientDtoOne.name())).thenReturn(Optional.of(ingredientOne));
        when(ingredientRepository.findByName(newIngredientDtoTwo.name())).thenReturn(Optional.of(ingredientTwo));

        dish.assignIngredient(ingredientOne);
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

        IngredientDto ingredientDtoOne = new IngredientDto(
                UUID.randomUUID(),
                "Pasta"
        );

        IngredientDto ingredientDtoTwo = new IngredientDto(
                UUID.randomUUID(),
                "Tomato"
        );

        Set<NewIngredientDto> ingredientDtoSet = Set.of(newIngredientDtoOne, newIngredientDtoTwo);

        // when
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(ingredientRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(ingredientService.saveNewIngredient(newIngredientDtoOne)).thenReturn(ingredientDtoOne);
        when(ingredientService.saveNewIngredient(newIngredientDtoTwo)).thenReturn(ingredientDtoTwo);
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
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoOne.category().toLowerCase())).thenReturn(Optional.of(dishCategoryOne));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoTwo.category().toLowerCase())).thenReturn(Optional.of(dishCategoryTwo));

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
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoOne.category().toLowerCase())).thenReturn(Optional.of(dishCategoryOne));
        when(dishCategoryRepository.findByCategory(newDishCategoryDtoTwo.category().toLowerCase())).thenReturn(Optional.of(dishCategoryTwo));

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

        DishCategoryDto dishCategoryDtoOne = new DishCategoryDto(
                UUID.randomUUID(),
                "Pasta"
        );

        DishCategoryDto dishCategoryDtoTwo = new DishCategoryDto(
                UUID.randomUUID(),
                "Tomato"
        );


        Set<NewDishCategoryDto> newDishCategoryDtoSet = Set.of(newDishCategoryDtoOne, newDishCategoryDtoTwo);

        // when
        when(validation.validateEntityById(dishId)).thenReturn(dish);
        when(dishCategoryRepository.findByCategory(anyString())).thenReturn(Optional.empty());
        when(dishCategoryService.saveDishCategory(newDishCategoryDtoOne)).thenReturn(dishCategoryDtoOne);
        when(dishCategoryService.saveDishCategory(newDishCategoryDtoTwo)).thenReturn(dishCategoryDtoTwo);

        service.assignDishCategoryToDish(dishId, newDishCategoryDtoSet);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getCategories().size(), 2);
    }
}