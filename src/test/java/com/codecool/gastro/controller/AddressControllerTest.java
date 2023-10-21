package com.codecool.gastro.controller;


import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.security.WebSecurityConfig;
import com.codecool.gastro.service.AddressService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AddressControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AddressService service;
    @MockBean
    RestaurantRepository restaurantRepository;

    private UUID addressId;
    private UUID restaurantId;
    private AddressDto addressDto;
    private String contentResponse;

    @BeforeEach
    void setUp() {
        restaurantId = UUID.fromString("41d35b7d-e4cb-4687-b56c-b4b9eb588241");
        addressId = UUID.fromString("4e3dbfd4-ada7-4273-863c-d018f402917a");

        addressDto = new AddressDto(
                addressId,
                "Poland",
                "City",
                "59-900",
                "Street",
                "15B",
                ""
        );

        contentResponse = """
                {
                    "id": "4e3dbfd4-ada7-4273-863c-d018f402917a",
                    "country": "Poland",
                    "city": "City",
                    "postalCode": "59-900",
                    "street": "Street",
                    "streetNumber": "15B",
                    "additionalDetails": ""
                }
                """;
    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnStatusNotFound_WhenNoAddress() throws Exception {
        // when
        when(service.getAddressByRestaurantId(restaurantId))
                .thenThrow(new ObjectNotFoundException(restaurantId, Restaurant.class));

        // then
        mockMvc.perform(get("/api/v1/addresses")
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class "
                                + Restaurant.class.getSimpleName() + " and id " + restaurantId + " cannot be found"));
    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnStatusOkAndAddressDto_WhenAddressWithValidRestaurantIdExist()
            throws Exception {
        // when
        when(service.getAddressByRestaurantId(restaurantId)).thenReturn(addressDto);

        // then
        mockMvc.perform(get("/api/v1/addresses")
                        .param("restaurantId", String.valueOf(restaurantId)))
                .andExpectAll(status().isOk(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testUpdateAddress_ShouldReturnStatusCreated_WhenProvidingValidData() throws Exception {
        // given
        String contentRequest = """
                {
                    "country": "Poland",
                    "city": "City",
                    "postalCode": "59-900",
                    "street": "Street",
                    "streetNumber": "15B",
                    "additionalDetails": "",
                    "restaurantId": "41d35b7d-e4cb-4687-b56c-b4b9eb588241"
                }
                """;

        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.updateAddress(any(UUID.class), any(NewAddressDto.class))).thenReturn(addressDto);

        // then
        mockMvc.perform(put("/api/v1/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse));
    }


    @Test
    void testUpdateAddress_ShouldReturnErrorMessages_WhenProvidingInvalidData() throws Exception {
        // given
        String contentRequest = """
                {
                    "country": "",
                    "city": "",
                    "postalCode": "",
                    "street": "",
                    "streetNumber": "",
                    "additionalDetails": ""
                }
                """;

        // then
        mockMvc.perform(put("/api/v1/addresses/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Country cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("City cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Postal code cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street number cannot be empty"))
                );
    }

}