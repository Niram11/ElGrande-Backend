package com.codecool.gastro.controler;

import com.codecool.gastro.controler.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.controler.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.service.BusinessHourService;
import com.codecool.gastro.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/business-hour")
public class BusinessHourController {
    private final BusinessHourService businessHourService;

    public BusinessHourController(BusinessHourService businessHourService) {
        this.businessHourService = businessHourService;
    }

    @GetMapping
    public List<BusinessHourDTO> getAllBusinessHours() {
        return businessHourService.getBusinessHours();
    }

    @GetMapping("/{id}")
    public BusinessHourDTO getBusinessHour(@PathVariable UUID id) {
        return businessHourService.getBusinessHourById(id);
    }

    @PostMapping
    public BusinessHourDTO createNewBusinessHour(@Valid @RequestBody NewBusinessHourDTO newBusinessHourDTO) {
        return businessHourService.saveNewBusinessHour(newBusinessHourDTO);
    }

}
