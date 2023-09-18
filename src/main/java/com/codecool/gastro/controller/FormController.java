package com.codecool.gastro.controller;


import com.codecool.gastro.dto.form.NewFormDto;
import com.codecool.gastro.service.FormService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping
    public void provideForm(@RequestBody NewFormDto newFormDto) {
        formService.provideForm(newFormDto);
    }
}
