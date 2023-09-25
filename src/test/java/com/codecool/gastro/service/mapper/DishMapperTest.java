package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.dish.DishDto;
import com.codecool.gastro.dto.dish.NewDishDto;
import com.codecool.gastro.repository.entity.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DishMapperTest {

    private DishMapper mapper = Mappers.getMapper(DishMapper.class);

    private UUID dishId;
    private UUID restaurantId;
    private Dish dish;
    private NewDishDto newDishDto;


    @BeforeEach
    void setUp() {
        dishId = UUID.fromString("05e5bd97-d8ac-4b9b-95e8-a397ad91d02a");
        restaurantId = UUID.fromString("6dbec6bb-3248-468d-bf1a-6cb9fb0f2bc6");

        dish = new Dish();
        dish.setId(dishId);
        dish.setDishName("Spaghetti");
        dish.setPrice(BigDecimal.valueOf(12.3));

        newDishDto = new NewDishDto(
                "Pizza",
                BigDecimal.valueOf(15.3),
                restaurantId
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
    void testDtoToDish_ShouldReturnDish_WhenCalled() {
        // when
        Dish dishOne = mapper.dtoToDish(dishId);
        Dish dishTwo = mapper.dtoToDish(dishId, newDishDto);
        Dish dishThree = mapper.dtoToDish(newDishDto);

        // then
        assertEquals(dishOne.getId(), dishId);
        assertEquals(dishTwo.getId(), dishId);
        assertEquals(dishTwo.getDishName(), newDishDto.dishName());
        assertEquals(dishTwo.getPrice(), newDishDto.price());
        assertEquals(dishTwo.getRestaurant().getId(), newDishDto.restaurantId());
        assertEquals(dishThree.getDishName(), newDishDto.dishName());
        assertEquals(dishThree.getPrice(), newDishDto.price());
        assertEquals(dishThree.getRestaurant().getId(), newDishDto.restaurantId());
    }
}