package com.codecool.gastro.controller;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.PromotedLocalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/promoted-locals")
public class PromotedLocalController {
    private final PromotedLocalService promotedLocalService;

    public PromotedLocalController(PromotedLocalService promotedLocalService) {
        this.promotedLocalService = promotedLocalService;
    }

    @GetMapping
    public List<PromotedLocalDto> getAllRestaurants() {
        return promotedLocalService.getPromotedLocals();
    }

    @GetMapping("/{id}")
    public PromotedLocalDto getAllRestaurants(@PathVariable UUID id) {
        return promotedLocalService.getPromotedLocalBy(id);
    }

    @PostMapping
    public PromotedLocalDto createNewRestaurant(@Valid @RequestBody NewPromotedLocalDto newPromotedLocalDto) {
        return promotedLocalService.saveNewPromotedLocal(newPromotedLocalDto);
    }

    @PutMapping("/{id}")
    public PromotedLocalDto updateRestaurant(@PathVariable UUID id, @Valid @RequestBody NewPromotedLocalDto newPromotedLocalDto) {
        return promotedLocalService.updatePromotedLocal(id, newPromotedLocalDto);
    }

    @DeleteMapping("/{id}")
    public void softDeleteRestaurant(@PathVariable UUID id) {
        promotedLocalService.deletePromotedLocal(id);
    }
}
