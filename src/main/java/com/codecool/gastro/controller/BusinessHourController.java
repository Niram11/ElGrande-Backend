package com.codecool.gastro.controller;

import com.codecool.gastro.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.service.BusinessHourService;
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

    @PutMapping("/{id}")
    public BusinessHourDTO updateBusinessHour(@PathVariable UUID id, @Valid @RequestBody NewBusinessHourDTO newBusinessHourDTO) {
        return businessHourService.updateBusinessHour(id, newBusinessHourDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBusinessHour(@PathVariable UUID id) {
        businessHourService.deleteBusinessHour(id);
    }

}
