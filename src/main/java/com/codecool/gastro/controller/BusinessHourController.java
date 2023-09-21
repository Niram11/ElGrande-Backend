package com.codecool.gastro.controller;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.service.BusinessHourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BusinessHourDto>> getAllBusinessHours() {
        return ResponseEntity.status(HttpStatus.OK).body(businessHourService.getBusinessHours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessHourDto> getBusinessHour(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(businessHourService.getBusinessHourById(id));
    }

    @GetMapping(params = {"restaurantId"})
    public ResponseEntity<List<BusinessHourDto>> getBusinessHoursByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(businessHourService.getBusinessHoursByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<BusinessHourDto> createNewBusinessHour(@Valid @RequestBody NewBusinessHourDto newBusinessHourDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessHourService.saveNewBusinessHour(newBusinessHourDto));
    }

    @PostMapping("/list")
    public ResponseEntity<List<BusinessHourDto>> createMultipleNewBusinessHour(@Valid @RequestBody List<NewBusinessHourDto> newBusinessHourDtoList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessHourService.saveMultipleNewBusinessHour(newBusinessHourDtoList));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BusinessHourDto> updateBusinessHour(@PathVariable UUID id, @Valid @RequestBody NewBusinessHourDto newBusinessHourDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessHourService.updateBusinessHour(id, newBusinessHourDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BusinessHourDto> deleteBusinessHour(@PathVariable UUID id) {
        businessHourService.deleteBusinessHour(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
