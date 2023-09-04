package com.codecool.gastro.controler;


import com.codecool.gastro.DTO.restaurantrestaurantcategory.NewRestaurantRestaurantCategoryDTO;
import com.codecool.gastro.DTO.restaurantrestaurantcategory.RestaurantRestaurantCategoryDTO;
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
    public List<RestaurantRestaurantCategoryDTO> getRestaurantRestaurantCategories() {
        return restaurantRestaurantCategoryService.getRestaurantRestaurantCategories();
    }

    @GetMapping("/{id}")
    public RestaurantRestaurantCategoryDTO getRestaurantRestaurantCategoryByID(@PathVariable UUID id) {
        return restaurantRestaurantCategoryService.getRestaurantRestaurantCategoryByUUID(id);
    }

    @PostMapping
    public RestaurantRestaurantCategoryDTO createNewRestaurantRestaurantCategory(@Valid @RequestBody
                                                                                 NewRestaurantRestaurantCategoryDTO newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryService.saveRestaurantRestaurantCategory(newRestaurantRestaurantCategoryDTO);
    }

    @PutMapping("/{id}")
    public RestaurantRestaurantCategoryDTO updateRestaurantRestaurantCategory(@PathVariable UUID id, @Valid @RequestBody
    NewRestaurantRestaurantCategoryDTO newRestaurantRestaurantCategoryDTO) {
        return restaurantRestaurantCategoryService.updateRestaurantRestaurantCategory
                (id,newRestaurantRestaurantCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurantRestaurantCategory(@PathVariable UUID id) {
        restaurantRestaurantCategoryService.deleteRestaurantRestaurantCategory(id);
    }
}
