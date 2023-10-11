package com.codecool.gastro.controller;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.service.form.service.RestaurantFormService;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FormController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantFormService service;

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
                        "contactNumber": 123,
                        "contactEmail": "awea"
                    },
                    "businessHour": [
                        {
                            "dayOfWeek": 8,
                            "openingHour": null,
                            "closingHour": null
                        },
                        {
                            "dayOfWeek": 0,
                            "openingHour": "11:30",
                            "closingHour": "23:00"
                        },
                        {
                            "dayOfWeek": null,
                            "openingHour": "11:30:31",
                            "closingHour": "23:00"
                        }
                    ],
                    "location": {
                        "latitude": null,
                        "longitude": null
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
        doThrow(ConstraintViolationException.class).when(service).submitRestaurantForm(any(NewRestaurantFormDto.class));

        // then
        mockMvc.perform(post("/api/v1/forms/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Description cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Invalid email")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Latitude cannot be null")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Longitude cannot be null")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Day of week must be greater then or equal 1")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Day of week must be less then or equal 7")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Day of week cannot be null")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Opening hour cannot be null")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Closing hour cannot be null")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Time must be in format HH:mm")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Country cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("City cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Postal code cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Street cannot be empty")),
                        jsonPath("$.errorMessage",
                                Matchers.containsString("Street number cannot be empty"))
                );
    }
}