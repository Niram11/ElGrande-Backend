package com.codecool.gastro.controller;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.service.BusinessHourService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/business-hours")
public class BusinessHourController {
    private final BusinessHourService businessHourService;

    public BusinessHourController(BusinessHourService businessHourService) {
        this.businessHourService = businessHourService;
    }

    @GetMapping
    public List<BusinessHourDto> getAllBusinessHours() {
        return businessHourService.getBusinessHours();
    }

    @GetMapping("/{id}")
    public BusinessHourDto getBusinessHour(@PathVariable UUID id) {
        return businessHourService.getBusinessHourBy(id);
    }

    @GetMapping(params = {"restaurantId"})
    public List<BusinessHourDto> getBusinessHoursByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return businessHourService.getBusinessHoursByRestaurantId(restaurantId);
    }

    @PostMapping
    public BusinessHourDto createNewBusinessHour(@Valid @RequestBody NewBusinessHourDto newBusinessHourDto) {
        return businessHourService.saveNewBusinessHour(newBusinessHourDto);
    }

    @PutMapping("/{id}")
    public BusinessHourDto updateBusinessHour(@PathVariable UUID id, @Valid @RequestBody NewBusinessHourDto newBusinessHourDto) {
        return businessHourService.updateBusinessHour(id, newBusinessHourDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBusinessHour(@PathVariable UUID id) {
        businessHourService.deleteBusinessHour(id);
    }

}
