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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BusinessHourController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BusinessHourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessHourService service;

    @MockBean
    private RestaurantRepository restaurantRepository;

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
                LocalTime.of(17, 30)
        );

        newBusinessHourDto = new NewBusinessHourDto(
                2,
                LocalTime.of(13, 30),
                LocalTime.of(18, 30),
                restaurantId
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
        // when
        when(service.getBusinessHourById(businessHourId)).thenReturn(businessHourDto);

        // test
        mockMvc.perform(get("/api/v1/business-hours/" + businessHourId))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(businessHourDto.id().toString()),
                        jsonPath("$.dayOfWeek").value(businessHourDto.dayOfWeek()),
                        jsonPath("$.openingHour", Matchers.containsString(businessHourDto.openingHour().toString())),
                        jsonPath("$.closingHour", Matchers.containsString(businessHourDto.closingHour().toString()))
                );
    }

    @Test
    void testGetBusinessHourById_ShouldReturnErrorMessage_WhenNoBusinessHours() throws Exception {
        // when
        when(service.getBusinessHourById(businessHourId)).thenThrow(new ObjectNotFoundException(businessHourId, BusinessHour.class));
        // test
        mockMvc.perform(get("/api/v1/business-hours/" + businessHourId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class BusinessHour and id " + businessHourId + " cannot be found")
                );
    }

    @Test
    void testGetBusinessHoursByRestaurantId_ShouldReturnStatusOkAndEmptyList_WhenRestaurantExist() throws Exception {
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

        contentResponse = """
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
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateNewBusinessHourAndUpdateBusinessHour_ShouldReturnStatusCreatedAndBusinessHourDto_WhenCalled() throws Exception {
        // given
        String contentRequest = """
                {
                    "dayOfWeek": 2,
                    "openingHour": "13:30",
                    "closingHour": "18:30",
                    "restaurantId": "180d99e9-0dcc-4f64-9ea6-32604f8ffd09"
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.saveNewBusinessHour(newBusinessHourDto)).thenReturn(businessHourDto);
        when(service.updateBusinessHour(businessHourId, newBusinessHourDto)).thenReturn(businessHourDto);

        // test
        mockMvc.perform(post("/api/v1/business-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );

        mockMvc.perform(put("/api/v1/business-hours/" + businessHourId)
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

    @Test
    void testCreateMultipleNewBusinessHour_ShouldReturnStatusCreatedAndListOfBusinessHour_WhenCalled() throws Exception {
        // given
        String contentRequest = """
                [
                    {
                        "dayOfWeek": 2,
                        "openingHour": "13:30",
                        "closingHour": "18:30",
                        "restaurantId": "180d99e9-0dcc-4f64-9ea6-32604f8ffd09"
                    }
                ]
                """;

        contentResponse = """
                [
                    {
                        "id": "e7b0b987-fcbd-4ea4-8f97-a1244cb81904",
                        "dayOfWeek": 1,
                        "openingHour": "12:30:00",
                        "closingHour": "17:30:00"
                    }
                ]
                """;

        // when
        when(service.saveMultipleNewBusinessHour(List.of(newBusinessHourDto)))
                .thenReturn(List.of(businessHourDto));

        // test
        mockMvc.perform(post("/api/v1/business-hours/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

}
