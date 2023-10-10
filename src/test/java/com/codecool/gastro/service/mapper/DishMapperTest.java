package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.EditDishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.repository.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DishMapperTest {
    DishMapper mapper = Mappers.getMapper(DishMapper.class);

    private UUID dishId;
    private Dish dish;
    private NewDishDto newDishDto;
    private EditDishDto editDishDto;
    
    @BeforeEach
    void setUp() {
        dishId = UUID.fromString("05e5bd97-d8ac-4b9b-95e8-a397ad91d02a");

        dish = new Dish();
        dish.setId(dishId);
        dish.setDishName("Spaghetti");
        dish.setPrice(BigDecimal.valueOf(12.3));

        newDishDto = new NewDishDto(
                "Pizza",
                BigDecimal.valueOf(15.3),
                UUID.randomUUID()
        );

        editDishDto = new EditDishDto(
                "Tomato-Soup",
                BigDecimal.valueOf(8.3)
        );
    }

    @Test
    void testToDto_ShouldReturnDishDto_WhenCalled() {
        // when
        DishDto dishDto = mapper.toDto(dish);

        // then
        assertEquals(dishDto.id(), dish.getId());
        assertEquals(dishDto.dishName(), dish.getDishName());
        assertEquals(dishDto.price(), dish.getPrice());
        assertEquals(dishDto.ingredients().size(), dish.getIngredients().size());
        assertEquals(dishDto.categories().size(), dish.getCategories().size());
    }

    @Test
    void testDtoToDish_ShouldReturnDish_WhenProvidedDto() {
        // when
        Dish testedDish = mapper.dtoToDish(newDishDto);

        // then
        assertEquals(testedDish.getDishName(), newDishDto.dishName());
        assertEquals(testedDish.getPrice(), newDishDto.price());
    }
    
    @Test
    void testDtoToDish_ShouldReturnDish_WhenProvidedId() {
        // when
        Dish testedDish = mapper.dtoToDish(dishId);

        // then
        assertEquals(testedDish.getId(), dishId);
    }

    @Test
    void testUpdateDishFromDto_ShouldUpdateOnlyFiledThatAreInDtoRestShouldBeSame_WhenCalled() {
        // when
        mapper.updatedDishFromDto(editDishDto, dish);

        // then
        assertEquals(dish.getPrice(), editDishDto.price());
        assertEquals(dish.getDishName(), editDishDto.dishName());
        assertNotNull(dish.getCategories());
        assertNotNull(dish.getIngredients());
    }
}