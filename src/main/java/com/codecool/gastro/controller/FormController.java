package com.codecool.gastro.controller;


import com.codecool.gastro.dto.form.NewFormRestaurantDto;
import com.codecool.gastro.service.FormService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/restaurant")
    public void provideForm(@Valid @RequestBody NewFormRestaurantDto newFormRestaurantDto) {
        formService.provideForm(newFormRestaurantDto);
    }
}
