package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.DetailedRestaurantDto;
import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.RestaurantService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    void testGetRestaurantByIdShouldReturnStatusNotFoundWhenNoRestaurant() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(service.getRestaurantById(id)).thenThrow(new ObjectNotFoundException(id, Restaurant.class));

        // then
        mockMvc.perform(get("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(("$.errorMessage")).value("Object of class Restaurant and id " + id + " cannot be found"));
    }

    @Test
    void testGetRestaurantByIdShouldReturnStatusOkAndRestaurantDtoWhenRestaurantExist() throws Exception {
        // given
        DetailedRestaurantDto restaurantDto = new DetailedRestaurantDto(
                UUID.randomUUID(),
                "Name",
                "Desc",
                "Website.pl",
                123123123,
                "Email@gmail.com",
                new String[]{"image1", "image2"},
                BigDecimal.valueOf(12)
        );

        // when
        when(service.getRestaurantById(restaurantDto.id())).thenReturn(restaurantDto);

        // then
        mockMvc.perform(get("/api/v1/restaurants/" + restaurantDto.id()))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(restaurantDto.id().toString()),
                        jsonPath("$.name").value(restaurantDto.name()),
                        jsonPath("$.description").value(restaurantDto.description()),
                        jsonPath("$.website").value(restaurantDto.website()),
                        jsonPath("$.contactNumber").value(restaurantDto.contactNumber().toString()),
                        jsonPath("$.contactEmail").value(restaurantDto.contactEmail()),
//                        jsonPath("$.imagesPaths").value(Arrays.asList(restaurantDto.imagesPaths()).toString()),
                        jsonPath("$.averageGrade").value(restaurantDto.averageGrade())
                );
    }

    @Test
    void testSoftDeleteRestaurantShouldReturnStatusNoContentWhenRestaurantExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // then
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSoftDeleteRestaurantShouldReturnStatusNotFoundWhenNoRestaurant() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        //when
        doThrow(new ObjectNotFoundException(id, Restaurant.class)).when(service).softDelete(id);

        // then
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTopRestaurantsDetailedShouldReturnStatusOkAndListOfDetailedRestaurantDto_WhenCalled() throws Exception {
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

        // then
        mockMvc.perform(get("/api/v1/restaurants")
                        .param("size", "1")
                        .param("page", "0")
                        .param("sort", "name"))
                .andExpectAll(status().isOk(),
                        content().json(contentRespond)
                );
    }

    @Test
    void testUpdateRestaurantShouldReturnStatusNotFoundWhenUpdatedRestaurantDoesNotExist() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto("UpdatedName", "UpdatedDesc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");

        // when
        when(service.updateRestaurant(eq(restaurantId), any(NewRestaurantDto.class))).thenThrow(new ObjectNotFoundException(restaurantId, Restaurant.class));

        // then
        mockMvc.perform(put("/api/v1/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newRestaurantDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Object of class Restaurant and id " + restaurantId + " cannot be found"));
    }

    @Test
    void testUpdateRestaurantWithInvalidDataShouldReturnBadRequest() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto("", "UpdatedDesc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");

        // when
        when(service.updateRestaurant(eq(restaurantId), any(NewRestaurantDto.class)))
                .thenThrow(new ObjectNotFoundException(restaurantId, Restaurant.class));

        // then
        mockMvc.perform(put("/api/v1/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newRestaurantDto)))
                .andExpectAll(status().isUnsupportedMediaType(),
                        jsonPath("$.errorMessage").value("Name cannot be empty")
                );
    }

    @Test
    void testUpdateRestaurantShouldReturnStatusOkWhenRestaurantIsUpdatedSuccessfully() throws Exception {
        // given
        UUID restaurantId = UUID.randomUUID();
        NewRestaurantDto newRestaurantDto = new NewRestaurantDto("UpdatedName", "UpdatedDesc", "UpdatedWebsite.pl", 555555555, "UpdatedEmail@gmail.com");
        RestaurantDto updatedRestaurantDto = new RestaurantDto(restaurantId, newRestaurantDto.name(), newRestaurantDto.description(), newRestaurantDto.website(), newRestaurantDto.contactNumber(), newRestaurantDto.contactEmail());

        // when
        when(service.updateRestaurant(ArgumentMatchers.eq(restaurantId), ArgumentMatchers.any(NewRestaurantDto.class))).thenReturn(updatedRestaurantDto);

        // then
        mockMvc.perform(put("/api/v1/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newRestaurantDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(restaurantId.toString()))
                .andExpect(jsonPath("$.name").value(updatedRestaurantDto.name()))
                .andExpect(jsonPath("$.description").value(updatedRestaurantDto.description()))
                .andExpect(jsonPath("$.website").value(updatedRestaurantDto.website()))
                .andExpect(jsonPath("$.contactNumber").value(updatedRestaurantDto.contactNumber().toString()))
                .andExpect(jsonPath("$.contactEmail").value(updatedRestaurantDto.contactEmail()));
    }
    //TODO: add tests for get detailed restaurant (not now)
}
