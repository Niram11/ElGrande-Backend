package com.codecool.gastro.controller;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.BusinessHourService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BusinessHourController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BusinessHourControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BusinessHourService service;
    @MockBean
    RestaurantRepository restaurantRepository;

    private UUID businessHourId;
    private UUID restaurantId;
    private BusinessHourDto businessHourDto;
    private NewBusinessHourDto newBusinessHourDto;
    private String contentResponse;

    @BeforeEach
    void setUp() {
        businessHourId = UUID.fromString("e7b0b987-fcbd-4ea4-8f97-a1244cb81904");
        restaurantId = UUID.fromString("180d99e9-0dcc-4f64-9ea6-32604f8ffd09");

        businessHourDto = new BusinessHourDto(
                businessHourId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(17, 30),
                false
        );

        newBusinessHourDto = new NewBusinessHourDto(
                2,
                LocalTime.of(13, 30),
                LocalTime.of(18, 30)
        );

        contentResponse = """
                {
                    "id": "e7b0b987-fcbd-4ea4-8f97-a1244cb81904",
                    "dayOfWeek": 1,
                    "openingHour": "12:30:00",
                    "closingHour": "17:30:00"
                }
                """;
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnStatusOkAndEmptyList_WhenRestaurantExist() throws Exception {
        // when
        when(service.getBusinessHoursByRestaurantId(restaurantId)).thenReturn(List.of());

        // then
        mockMvc.perform(get("/api/v1/business-hours")
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnStatusOkAndListOfBusinessHoursDto_WhenRestaurantExist() throws Exception {
        // when
        when(service.getBusinessHoursByRestaurantId(restaurantId)).thenReturn(List.of());

        // then
        mockMvc.perform(get("/api/v1/business-hours")
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testUpdateBusinessHour_ShouldReturnStatusCreatedAndBusinessHourDto_WhenCalled() throws Exception {
        // given
        String contentRequest = """
                {
                    "dayOfWeek": 2,
                    "openingHour": "13:30",
                    "closingHour": "18:30"
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.updateBusinessHour(businessHourId, newBusinessHourDto)).thenReturn(businessHourDto);

        // then
        mockMvc.perform(put("/api/v1/business-hours/" + businessHourId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @ParameterizedTest
    @MethodSource("getArgsForBusinessHourValidation")
    void testUpdateBusinessHour_ShouldReturnStatusBadRequestWithErrorMessages_WhenProvidingInvalidData(
            String dayOfWeek, String openingHour, String closingHour, String dayErrMsg, String openingHourErrMsg,
            String closingHourErrMsg) throws Exception {
        // given
        String content = """
                {
                    "dayOfWeek": "%s",
                    "openingHour":"%s",
                    "closingHour": "%s",
                    "restaurantId": null
                }
                """.formatted(dayOfWeek, openingHour, closingHour);

        // then
        mockMvc.perform(put("/api/v1/business-hours/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString(dayErrMsg)),
                        jsonPath("$.errorMessage", Matchers.containsString(openingHourErrMsg)),
                        jsonPath("$.errorMessage", Matchers.containsString(closingHourErrMsg))
                );
    }

    @Test
    void testDeleteBusinessHour_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // then
        mockMvc.perform(delete("/api/v1/business-hours/" + UUID.randomUUID()))
                .andExpectAll(status().isNoContent());
    }

    private static Stream<Arguments> getArgsForBusinessHourValidation() {
        return Stream.of(
                Arguments.of("", "", "", "Day of week cannot be null", "Opening hour cannot be null",
                        "Closing hour cannot be null"),
                Arguments.of("0", "12:12:12", "12:12:12", "Day of week must be greater then or equal 1",
                        "Time must be in format HH:mm", "Time must be in format HH:mm"),
                Arguments.of("8", "12:12:12", "12:12:12", "Day of week must be less then or equal 7",
                        "Time must be in format HH:mm", "Time must be in format HH:mm")
        );
    }
}
