package com.codecool.gastro.controler;


import com.codecool.gastro.dto.restaurantcategory.NewRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurantCategory")
public class RestaurantCategoryController {

    private final RestaurantCategoryService restaurantCategoryService;

    public RestaurantCategoryController(RestaurantCategoryService restaurantCategoryService1) {
        this.restaurantCategoryService = restaurantCategoryService1;
    }

    @GetMapping
    public List<RestaurantCategoryDto> getAllRestaurantCategories() {
        return restaurantCategoryService.getRestaurantCategories();
    }

    @GetMapping("/{id}")
    public RestaurantCategoryDto getRestaurantCategoryById(@PathVariable UUID id) {
        return restaurantCategoryService.getRestaurantCategoryByUUID(id);
    }

    @PostMapping
    public RestaurantCategoryDto createNewRestaurantCategory(@Valid @RequestBody NewRestaurantCategoryDto
                                                                         newRestaurantCategoryDTO) {
        return restaurantCategoryService.saveRestaurantCategory(newRestaurantCategoryDTO);
    }

    @PutMapping("/{id}")
    public RestaurantCategoryDto updateRestaurantCategory(@PathVariable UUID id
            , @Valid @RequestBody NewRestaurantCategoryDto newRestaurantCategoryDTO) {
        return restaurantCategoryService.updateRestaurantCategory(id,newRestaurantCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantCategory(@PathVariable UUID id) {
        restaurantCategoryService.deleteRestaurantCategory(id);
    }
}
