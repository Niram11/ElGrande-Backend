package com.codecool.gastro.controller;


import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.service.form.service.RestaurantFormService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forms")
public class FormController {

    private final RestaurantFormService formService;

    public FormController(RestaurantFormService formService) {
        this.formService = formService;
    }

    @PostMapping("/restaurant")
    public ResponseEntity<NewRestaurantFormDto> createNewRestaurant(@Valid @RequestBody NewRestaurantFormDto newFormRestaurantDto) {
        formService.submitRestaurantForm(newFormRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
