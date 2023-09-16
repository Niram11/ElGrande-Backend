package com.codecool.gastro.controller;

import com.codecool.gastro.dto.restaurant.NewRestaurantDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.RestaurantService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService service;

    @Test
    void testGetRestaurantById_ShouldReturn404_WhenNoRestaurant() throws Exception {
        // give
        UUID id = UUID.randomUUID();

        // when
        when(service.getRestaurantById(id)).thenThrow(new ObjectNotFoundException(id, Restaurant.class));

        // test
        mockMvc.perform(get("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(("$.errorMessage")).value("Object of class Restaurant and id " + id + " cannot be found"));
    }

    @Test
    void testGetAllRestaurants_ShouldReturn200_WhenCalled() throws Exception {
        // when
        when(service.getRestaurants()).thenReturn(new ArrayList<>());

        // test
        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRestaurantById_ShouldReturnRestaurantDtoAsJson_WhenExist() throws Exception {
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
                .andExpectAll(status().isFound(),
                        jsonPath(("$.id")).value(restaurantDto.id().toString()),
                        jsonPath(("$.name")).value(restaurantDto.name()),
                        jsonPath(("$.description")).value(restaurantDto.description()),
                        jsonPath(("$.website")).value(restaurantDto.website()),
                        jsonPath(("$.contactNumber")).value(restaurantDto.contactNumber().toString()),
                        jsonPath(("$.contactEmail")).value(restaurantDto.contactEmail())
                );
    }

    @Test
    void testCreateNewRestaurant_ShouldReturnCreatedRestaurantWithIdAsJson_WhenProvidedValidDataInBody() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        NewRestaurantDto newRestaurantDto = new NewRestaurantDto(
                "Name",
                "Desc",
                "Website.pl",
                123123123,
                "Email@wp.pl"
        );

        RestaurantDto restaurantDto = new RestaurantDto(
                id,
                newRestaurantDto.name(),
                newRestaurantDto.description(),
                newRestaurantDto.website(),
                newRestaurantDto.contactNumber(),
                newRestaurantDto.contactEmail()
        );

        String content = """
                {
                "name": "Name",
                "description": "Desc",
                "website": "Website.pl",
                "contactNumber": 123123123,
                "contactEmail": "Email@wp.pl"
                }
                """;

        // when
        when(service.saveNewRestaurant(newRestaurantDto)).thenReturn(restaurantDto);
        when(service.updateRestaurant(id, newRestaurantDto)).thenReturn(restaurantDto);

        // test
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));

        mockMvc.perform(put("/api/v1/restaurants/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void testCreateNewRestaurantAndUpdateRestaurant_ShouldThrowIllegalArgumentException_WhenProvidingInvalidJson() throws Exception {
        // given
        String content = """
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
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Name must be between 4 and 100 characters long")),
                        jsonPath("$.errorMessage", Matchers.containsString("Description cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Contact Number must be a 9-digit integer")),
                        jsonPath("$.errorMessage", Matchers.containsString("Invalid email"))
                );

        mockMvc.perform(put("/api/v1/restaurants/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Name must be between 4 and 100 characters long")),
                        jsonPath("$.errorMessage", Matchers.containsString("Description cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Contact Number must be a 9-digit integer")),
                        jsonPath("$.errorMessage", Matchers.containsString("Invalid email"))
                );
    }

    @Test
    void testCreateNewRestaurantAndUpdateRestaurant_ShouldThrowHttpMessageNotReadableException_WhenProvidingNotConvertableType() throws Exception {
        // given
        String content = """
                {
                "name": "Name",
                "description": "Desc",
                "website": "Website.pl",
                "contactNumber": 12345678901,
                "contactEmail": "Email@wp.pl"
                }
                """;

        //test
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", Matchers.containsString("One or more fields are not readable")));

        mockMvc.perform(put("/api/v1/restaurants/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpectAll(jsonPath("$.errorMessage", Matchers.containsString("One or more fields are not readable")));
    }

    @Test
    void testSoftDeleteRestaurant_ShouldReturnNoContentStatus_WhenProvidingExistingUUID() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // test
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSoftDeleteRestaurant_ShouldReturnNotFound_WhenNoRestaurant() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        //when
        doThrow(new ObjectNotFoundException(id, Restaurant.class)).when(service).softDelete(id);

        // test
        mockMvc.perform(delete("/api/v1/restaurants/" + id))
                .andExpect(status().isNotFound());
    }
}
