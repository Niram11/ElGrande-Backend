package com.codecool.gastro.controler;


import com.codecool.gastro.dto.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantrestaurantcategory.RestaurantRestaurantCategoryDto;
import com.codecool.gastro.service.RestaurantRestaurantCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurantRestaurantCategory")
public class RestaurantRestaurantCategoryController {

    private final RestaurantRestaurantCategoryService restaurantRestaurantCategoryService;

    public RestaurantRestaurantCategoryController(RestaurantRestaurantCategoryService restaurantRestaurantCategoryService) {
        this.restaurantRestaurantCategoryService = restaurantRestaurantCategoryService;
    }

    @GetMapping
    public List<RestaurantRestaurantCategoryDto> getRestaurantRestaurantCategories() {
        return restaurantRestaurantCategoryService.getRestaurantRestaurantCategories();
    }

    @GetMapping("/{id}")
    public RestaurantRestaurantCategoryDto getRestaurantRestaurantCategoryByID(@PathVariable UUID id) {
        return restaurantRestaurantCategoryService.getRestaurantRestaurantCategoryByUUID(id);
    }

    @PostMapping
    public RestaurantRestaurantCategoryDto createNewRestaurantRestaurantCategory(@Valid @RequestBody
                                                                                 NewRestaurantRestaurantCategoryDto newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryService.saveRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO);
    }

    @PutMapping("/{id}")
    public RestaurantRestaurantCategoryDto updateRestaurantRestaurantCategory(@PathVariable UUID id, @Valid @RequestBody
    NewRestaurantRestaurantCategoryDto newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryService.updateRestaurantRestaurantCategory
                (id,newRestaurantRestaurantCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantRestaurantCategory(@PathVariable UUID id) {
        restaurantRestaurantCategoryService.deleteRestaurantRestaurantCategory(id);
    }
}
