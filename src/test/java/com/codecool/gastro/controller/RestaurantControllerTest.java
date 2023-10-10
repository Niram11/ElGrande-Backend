package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.RestaurantService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService service;

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

    @Test
    void testUpdateRestaurant_ShouldReturnStatusCreatedAndUpdatedRestaurantDto_WhenUpdateIsSuccessful() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto("Updated Name", "Updated Desc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");
        RestaurantDto updatedRestaurantDto = new RestaurantDto(restaurantId, "Updated Name", "Updated Desc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");

        // when
        when(service.updateRestaurant(eq(restaurantId), any(NewRestaurantDto.class))).thenReturn(updatedRestaurantDto);

        // test
        mockMvc.perform(put("/api/v1/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newRestaurantDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(restaurantId.toString()))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testUpdateRestaurant_ShouldReturnStatusNotFound_WhenRestaurantToUpdateDoesNotExist() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto("Updated Name", "Updated Desc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");

        // when
        when(service.updateRestaurant(eq(restaurantId), any(NewRestaurantDto.class))).thenThrow(new ObjectNotFoundException(restaurantId, Restaurant.class));

        // test
        mockMvc.perform(put("/api/v1/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newRestaurantDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Object of class Restaurant and id " + restaurantId + " cannot be found"));
    }
}
