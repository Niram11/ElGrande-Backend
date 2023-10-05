package com.codecool.gastro.controller;


import com.codecool.gastro.dto.form.NewFormRestaurantDto;
import com.codecool.gastro.service.FormService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/restaurant")
    public ResponseEntity<NewFormRestaurantDto> createNewRestaurant(@Valid @RequestBody NewFormRestaurantDto newFormRestaurantDto) {
        formService.provideRestaurantForm(newFormRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
