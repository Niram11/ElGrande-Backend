package com.codecool.gastro.controller;

import com.codecool.gastro.dto.location.LocationDto;
import com.codecool.gastro.dto.location.NewLocationDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.service.LocationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LocationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LocationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    LocationService service;

    private UUID locationId;
    private LocationDto locationDto;
    private String contentResponse;

    @BeforeEach
    void setUp() {
        locationId = UUID.fromString("7afcab8c-4d61-4f7d-b6c8-bc69b2dfed43");

        Set<RestaurantDto> restaurants = new HashSet<>();
        RestaurantDto restaurantDto = new RestaurantDto(
                UUID.randomUUID(),
                "Restaurant Name",
                "Description",
                "www.restaurant.com",
                123456789,
                "contact@restaurant.com"
        );
        restaurants.add(restaurantDto);

        locationDto = new LocationDto(
                locationId,
                new BigDecimal("52.5200"),
                new BigDecimal("13.4050"),
                restaurants
        );

        contentResponse = """
                {
                    "id": "7afcab8c-4d61-4f7d-b6c8-bc69b2dfed43",
                    "latitude": 52.5200,
                    "longitude": 13.4050,
                    "restaurants": [
                        {
                            "id": "%s",
                            "name": "Restaurant Name",
                            "description": "Description",
                            "website": "www.restaurant.com",
                            "contactNumber": 123456789,
                            "contactEmail": "contact@restaurant.com"
                        }
                    ]
                }
                """.formatted(restaurantDto.id());
    }

    @Test
    void testCreateNewLocationShouldReturnStatusCreatedAndLocationDtoWhenValidValues() throws Exception {
        // when
        when(service.saveLocation(any(NewLocationDto.class))).thenReturn(locationDto);

        String requestBody = """
                {
                    "latitude": 52.5200,
                    "longitude": 13.4050
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    private static Stream<Arguments> getArgsForLocationValidation() {
        return Stream.of(
                Arguments.of(null, "Latitude cannot be null"),
                Arguments.of(new BigDecimal("52.5200"), "Longitude cannot be null")
        );
    }

    //TODO: to parametrized test
    @Test
    void testCreateNewLocationShouldReturnStatusBadRequestAndErrorMessagesWhenInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "latitude": null,
                    "longitude": 13.4050
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isUnsupportedMediaType(),
                        jsonPath("$.errorMessage", Matchers.containsString("Latitude cannot be null"))
                );
    }

    @Test
    void testCreateNewLocationShouldReturnStatusBadRequestAndErrorMessagesWhenInvalidLongitude() throws Exception {
        // given
        String contentRequest = """
                {
                    "latitude": 52.5200,
                    "longitude": null
                }
                """;

        // then
        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isUnsupportedMediaType(),
                        jsonPath("$.errorMessage", Matchers.containsString("Longitude cannot be null"))
                );
    }


    @Test
    void testAddRestaurantsToLocationShouldReturnStatusNoContentWhenValidValues() throws Exception {
        // Given
        UUID restaurantId = UUID.randomUUID();

        // When and Then
        mockMvc.perform(put("/api/v1/locations/{id}/restaurants", locationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        //TODO:to multiline string
                        .content("[{\"id\": \"" + restaurantId + "\", \"name\": \"Restaurant Name\"}]"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteLocationShouldReturnStatusNoContentWhenCalled() throws Exception {
        // then
        mockMvc.perform(delete("/api/v1/locations/" + locationId))
                .andExpectAll(status().isNoContent());
    }
}
