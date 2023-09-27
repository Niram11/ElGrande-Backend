package com.codecool.gastro.controller;

import com.codecool.gastro.dto.form.NewFormRestaurantDto;
import com.codecool.gastro.service.FormService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FormController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormService service;

    @Test
    void testCreateNewRestaurant_ShouldReturnStatusCreated_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "restaurant": {
                        "name": "Kacper",
                        "description": "KacperToJa",
                        "website": "Kacper.pl",
                        "contactNumber": 123123123,
                        "contactEmail": "Kacper@xd.pl"
                    },
                    "businessHour": [
                        {
                            "dayOfWeek": 3,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        },
                        {
                            "dayOfWeek": 3,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        }
                    ],
                    "location": {
                        "latitude": 43.234,
                        "longitude": 43.7654
                    },
                    "address": {
                        "country": "country",
                        "city": "city",
                        "postalCode": "postalCode",
                        "street": "street",
                        "streetNumber": "streetNumber",
                        "additionalDetails": "additionalDetails"
                    }
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/forms/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateNewRestaurant_ShouldReturnStatusBadRequest_WhenInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "restaurant": {
                        "name": "",
                        "description": "",
                        "website": "",
                        "contactNumber": null,
                        "contactEmail": ""
                    },
                    "businessHour": [
                        {
                            "dayOfWeek": 3,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        },
                        {
                            "dayOfWeek": 3,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        },
                        {
                            "dayOfWeek": 3,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        }
                    ],
                    "location": {
                        "latitude": 43.234,
                        "longitude": 43.7654
                    },
                    "address": {
                        "country": "",
                        "city": "",
                        "postalCode": "",
                        "street": "",
                        "streetNumber": "",
                        "additionalDetails": ""
                    }
                }
                """;
        // when
        doThrow(ConstraintViolationException.class).when(service).provideRestaurantForm(any(NewFormRestaurantDto.class));

        // then
        mockMvc.perform(post("/api/v1/forms/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage").value("One or more fields are does not meet requirements")
                );
    }
}