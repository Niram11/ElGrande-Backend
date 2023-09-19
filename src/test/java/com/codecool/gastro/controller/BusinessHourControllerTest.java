package com.codecool.gastro.controller;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.BusinessHourService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BusinessHourController.class)
public class BusinessHourControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BusinessHourService service;

    @MockBean
    RestaurantRepository restaurantRepository;

    @Test
    void testGetAllBusinessHours_ShouldReturnStatusOkAndListOfBusinessHourDto_WhenCalled() throws Exception {
        // when
        when(service.getBusinessHours()).thenReturn(List.of());

        // test
        mockMvc.perform(get("/api/v1/business-hours"))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetBusinessHourById_ShouldReturnStatusOkAndDto_WhenBusinessHoursExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        BusinessHourDto businessHourDto = new BusinessHourDto(
                id,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(17, 30)
        );

        // when
        when(service.getBusinessHourById(id)).thenReturn(businessHourDto);

        // test
        mockMvc.perform(get("/api/v1/business-hours/" + id))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(businessHourDto.id().toString()),
                        jsonPath("$.dayOfWeek").value(businessHourDto.dayOfWeek()),
                        jsonPath("$.openingHour", Matchers.containsString(businessHourDto.openingHour().toString())),
                        jsonPath("$.closingHour", Matchers.containsString(businessHourDto.closingHour().toString()))
                );
    }

    @Test
    void testGetBusinessHourById_ShouldReturnErrorMessage_WhenNoBusinessHours() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(service.getBusinessHourById(id)).thenThrow(new ObjectNotFoundException(id, BusinessHour.class));
        // test
        mockMvc.perform(get("/api/v1/business-hours/" + id))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class BusinessHour and id " + id + " cannot be found")
                );
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnStatusOkAndEmptyList_WhenRestaurantExist() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();

        // when
        when(service.getBusinessHoursByRestaurantId(restaurantId)).thenReturn(List.of());

        // test
        mockMvc.perform(get("/api/v1/business-hours?restaurantId=" + restaurantId))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnStatusOkAndListOfBusinessHoursDto_WhenRestaurantExist() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        UUID bhOneId = UUID.fromString("5ca6ea83-966a-4b4d-8a3a-416bbc5ce567");
        UUID bhTwoId = UUID.fromString("abe6d29b-043f-425d-a3b8-dd7cdc623379");

        BusinessHourDto businessHourDtoOne = new BusinessHourDto(
                bhOneId,
                1,
                LocalTime.of(12, 30),
                LocalTime.of(17, 30)
        );
        BusinessHourDto businessHourDtoTwo = new BusinessHourDto(
                bhTwoId,
                2,
                LocalTime.of(13, 30),
                LocalTime.of(18, 30)
        );

        String contentRespond = """
                [
                    {
                        "id": "5ca6ea83-966a-4b4d-8a3a-416bbc5ce567",
                        "dayOfWeek": 1,
                        "openingHour": "12:30:00",
                        "closingHour": "17:30:00"
                    },
                    {
                        "id": "abe6d29b-043f-425d-a3b8-dd7cdc623379",
                        "dayOfWeek": 2,
                        "openingHour": "13:30:00",
                        "closingHour": "18:30:00"
                    }
                ]
                """;

        // when
        when(service.getBusinessHoursByRestaurantId(restaurantId)).thenReturn(List.of(businessHourDtoOne, businessHourDtoTwo));

        //test
        mockMvc.perform(get("/api/v1/business-hours?restaurantId=" + restaurantId))
                .andExpectAll(status().isOk(),
                        content().json(contentRespond)
                );
    }

    @Test
    void testCreateNewBusinessHourAndUpdateBusinessHour_ShouldReturnStatusCreatedAndBusinessHourDto_WhenCalled() throws Exception {
        // given
        UUID bhId = UUID.fromString("9162eb15-ea1b-4a70-964b-6c4203d793c3");
        UUID restaurantId = UUID.fromString("73ef773d-6a33-449a-9ccd-e9e305450939");

        NewBusinessHourDto newBusinessHourDto = new NewBusinessHourDto(
                2,
                LocalTime.of(13, 30),
                LocalTime.of(18, 30),
                restaurantId
        );

        BusinessHourDto businessHourDto = new BusinessHourDto(
                bhId,
                2,
                LocalTime.of(13, 30),
                LocalTime.of(18, 30)
        );

        String contentRequest = """
                {
                    "dayOfWeek": 2,
                    "openingHour": "13:30",
                    "closingHour": "18:30",
                    "restaurantId": "73ef773d-6a33-449a-9ccd-e9e305450939"
                }
                """;

        String contentResponse = """
                {
                    "id": "9162eb15-ea1b-4a70-964b-6c4203d793c3",
                    "dayOfWeek": 2,
                    "openingHour": "13:30:00",
                    "closingHour": "18:30:00"
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.saveNewBusinessHour(newBusinessHourDto)).thenReturn(businessHourDto);
        when(service.updateBusinessHour(bhId, newBusinessHourDto)).thenReturn(businessHourDto);

        // test
        mockMvc.perform(post("/api/v1/business-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );

        mockMvc.perform(put("/api/v1/business-hours/" + bhId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateNewBusinessHourAndUpdateBusinessHour_ShouldReturnStatusBadRequestWithErrorMessages_WhenProvidingNullsInJson() throws Exception {
        // given
        String content = """
                {
                    "dayOfWeek": null,
                    "openingHour": null,
                    "closingHour": null,
                    "restaurantId": null
                }
                """;

        // test
        mockMvc.perform(post("/api/v1/business-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Opening hour cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Closing hour cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );

        mockMvc.perform(put("/api/v1/business-hours/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Opening hour cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Closing hour cannot be null")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );
    }

    @Test
    void testCreateNewBusinessHourAndUpdateBusinessHour_ShouldReturnStatusBadRequestWithErrorMessages_WhenProvidingWrongValuesDataInJson() throws Exception {
        // given
        String content = """
                {
                    "dayOfWeek": 0,
                    "openingHour": "12:30:32",
                    "closingHour": "12:30:32",
                    "restaurantId": ""
                }
                """;

        // when
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Restaurant()));

        mockMvc.perform(post("/api/v1/business-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week must be greater then or equal 1")),
                        jsonPath("$.errorMessage", Matchers.containsString("Time must be in format HH:mm"))
                );

        mockMvc.perform(put("/api/v1/business-hours/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week must be greater then or equal 1")),
                        jsonPath("$.errorMessage", Matchers.containsString("Time must be in format HH:mm"))
                );
    }

    @Test
    void testCreateNewBusinessHourAndUpdateBusinessHour_ShouldReturnStatusBadRequestWithErrorMessages_WhenProvidingWrongValuesForDayOfWeekInJson() throws Exception {
        // given
        String content = """
                {
                    "dayOfWeek": 8,
                    "openingHour": "12:30",
                    "closingHour": "12:30",
                    "restaurantId": ""
                }
                """;

        // when
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Restaurant()));

        mockMvc.perform(post("/api/v1/business-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week must be less then or equal 7"))
                );

        mockMvc.perform(put("/api/v1/business-hours/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Day of week must be less then or equal 7"))
                );
    }

    @Test
    void testDeleteBusinessHour_ShouldReturnStatusNoContent_WhenCalled() throws Exception {
        // test
        mockMvc.perform(delete("/api/v1/business-hours/" + UUID.randomUUID()))
                .andExpectAll(status().isNoContent());
    }
}
