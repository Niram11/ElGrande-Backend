package com.codecool.gastro.controler;


import com.codecool.gastro.DTO.restaurantcategory.NewRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantcategory.RestaurantCategoryDTO;
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
    public List<RestaurantCategoryDTO> getAllRestaurantCategories() {
        return restaurantCategoryService.getRestaurantCategories();
    }

    @GetMapping("/{id}")
    public RestaurantCategoryDTO getRestaurantCategoryById(@PathVariable UUID id) {
        return restaurantCategoryService.getRestaurantCategoryByUUID(id);
    }

    @PostMapping
    public RestaurantCategoryDTO createNewRestaurantCategory(@Valid @RequestBody NewRestaurantCategoryDTO
                                                                         newRestaurantCategoryDTO) {
        return restaurantCategoryService.saveRestaurantCategory(newRestaurantCategoryDTO);
    }

    @PutMapping("/{id}")
    public RestaurantCategoryDTO updateRestaurantCategory(@PathVariable UUID id
            , @Valid @RequestBody NewRestaurantCategoryDTO newRestaurantCategoryDTO) {
        return restaurantCategoryService.updateRestaurantCategory(id,newRestaurantCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantCategory(@PathVariable UUID id) {
        restaurantCategoryService.deleteRestaurantCategory(id);
    }
}
