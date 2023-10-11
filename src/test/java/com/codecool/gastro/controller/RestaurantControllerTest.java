package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.RestaurantService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService service;

    @Test
    void testGetAllRestaurants_ShouldReturnStatusOk_WhenCalled() throws Exception {
        // when
        when(service.getRestaurants()).thenReturn(new ArrayList<>());

        // test
        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRestaurantById_ShouldReturnStatusNotFound_WhenNoRestaurant() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(service.getRestaurantById(id)).thenThrow(new ObjectNotFoundException(id, Restaurant.class));

        // test
        mockMvc.perform(get("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(("$.errorMessage")).value("Object of class Restaurant and id " + id + " cannot be found"));
    }

    @Test
    void testGetRestaurantById_ShouldReturnStatusOkAndRestaurantDto_WhenRestaurantExist() throws Exception {
        // given
        RestaurantDto restaurantDto = new RestaurantDto(
                UUID.randomUUID(),
                "Name",
                "Desc",
                "Website.pl",
                123123123,
                "Email@gmail.com"
        );

        // when
        when(service.getRestaurantById(restaurantDto.id())).thenReturn(restaurantDto);

        // test
        mockMvc.perform(get("/api/v1/restaurants/" + restaurantDto.id()))
                .andExpectAll(status().isOk(),
                        jsonPath(("$.id")).value(restaurantDto.id().toString()),
                        jsonPath(("$.name")).value(restaurantDto.name()),
                        jsonPath(("$.description")).value(restaurantDto.description()),
                        jsonPath(("$.website")).value(restaurantDto.website()),
                        jsonPath(("$.contactNumber")).value(restaurantDto.contactNumber().toString()),
                        jsonPath(("$.contactEmail")).value(restaurantDto.contactEmail())
                );
    }

    @Test
    void testCreateNewRestaurant_ShouldReturnStatusOkAndRestaurantDto_WhenProvidedValidDataInBody() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        RestaurantDto restaurantDto = new RestaurantDto(
                id,
                "Name",
                "Desc",
                "Website.pl",
                123123123,
                "Email@wp.pl"
        );

        String contentRequest = """
                {
                "name": "Name",
                "description": "Desc",
                "website": "Website.pl",
                "contactNumber": 123123123,
                "contactEmail": "Email@wp.pl"
                }
                """;

        // when
        when(service.saveNewRestaurant(any(NewRestaurantDto.class))).thenReturn(restaurantDto);
        when(service.updateRestaurant(any(UUID.class), any(NewRestaurantDto.class))).thenReturn(restaurantDto);

        // test
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        jsonPath("$.id").value(id.toString()),
                        content().json(contentRequest));

        mockMvc.perform(put("/api/v1/restaurants/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        jsonPath("$.id").value(id.toString()),
                        content().json(contentRequest));

    }

    @Test
    void testCreateNewRestaurantAndUpdateRestaurant_ShouldReturnStatusBadRequest_WhenProvidingInvalidJson() throws Exception {
        // given

        String contentOne = """
                {
                "name": "",
                "description": "",
                "website": "Website.pl",
                "contactNumber": 1231231231,
                "contactEmail": "Emailwp.pl"
                }
                """;

        String contentTwo = """
                {
                "name": "",
                "description": "",
                "website": "Website.pl",
                "contactNumber": 1231231231,
                "contactEmail": "Emailwp.pl"
                }
                """;

        // test
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Description cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Invalid email"))
                );

        mockMvc.perform(put("/api/v1/restaurants/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentOne))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Description cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Invalid email"))
                );
    }

    @Test
    void testSoftDeleteRestaurant_ShouldReturnStatusNoContent_WhenRestaurantExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // test
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSoftDeleteRestaurant_ShouldReturnStatusNotFound_WhenNoRestaurant() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        //when
        doThrow(new ObjectNotFoundException(id, Restaurant.class)).when(service).softDelete(id);

        // test
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTopRestaurantsDetailed_ShouldReturnStatusOkAndListOfDetailedRestaurantDto_WhenCalled() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 1, Sort.by("name"));

        DetailedRestaurantDto detailedRestaurantDto = new DetailedRestaurantDto(
                UUID.fromString("080aec50-4978-46ae-b7ce-a92cf804e688"),
                "Przemek",
                "PrzemekToJa",
                "Przemek.pl",
                312312312,
                "Przemek@xd.pl",
                new String[]{"xd", "xd1", "xd12", "xd123"},
                BigDecimal.valueOf(5.7500000000000000)
        );

        String contentRespond = """
                [
                    {
                        "id": "080aec50-4978-46ae-b7ce-a92cf804e688",
                        "name": "Przemek",
                        "description": "PrzemekToJa",
                        "website": "Przemek.pl",
                        "contactNumber": 312312312,
                        "contactEmail": "Przemek@xd.pl",
                        "imagesPaths": [
                            "xd",
                            "xd1",
                            "xd12",
                            "xd123"
                        ],
                        "averageGrade": 5.7500000000000000
                    }
                ]
                """;

        // when
        when(service.getDetailedRestaurants(pageable)).thenReturn(List.of(detailedRestaurantDto));

        // test
        mockMvc.perform(get("/api/v1/restaurants")
                        .param("size", "1")
                        .param("page", "0")
                        .param("sort", "name"))
                .andExpectAll(status().isOk(),
                        content().json(contentRespond)
                );
    }
}
