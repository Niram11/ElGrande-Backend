package com.codecool.gastro.controller;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.service.form.service.FormService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/forms")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/restaurant")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NewRestaurantFormDto> createNewRestaurant(
            @Valid @RequestBody NewRestaurantFormDto newFormRestaurantDto) {
        formService.submitRestaurantForm(newFormRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
