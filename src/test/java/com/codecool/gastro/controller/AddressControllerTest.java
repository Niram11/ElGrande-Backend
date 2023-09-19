package com.codecool.gastro.controller;


import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.AddressService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    void testGetAllAddresses_ShouldReturnStatusOk_WhenCalled() throws Exception {
        // when
        when(service.getAddresses()).thenReturn(List.of());

        // test
        mockMvc.perform(get("/api/v1/addresses"))
                .andExpectAll(status().isOk(),
                        content().json("[]"));
    }

    @Test
    void testGetAddressById_ShouldReturnStatusNotFound_WhenNoAddress() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(service.getAddressById(id)).thenThrow(new ObjectNotFoundException(id, Address.class));

        // test
        mockMvc.perform(get("/api/v1/addresses/" + id))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Address and id " + id + " cannot be found"));
    }

    @Test
    void testGetAddressById_ShouldReturnStatusOkAndAddressDto_WhenAddressExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        AddressDto addressDto = new AddressDto(id,
                "Poland",
                "City",
                "59-900",
                "Street",
                "15B",
                "");

        // when
        when(service.getAddressById(id)).thenReturn(addressDto);

        // test
        mockMvc.perform(get("/api/v1/addresses/" + id))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(addressDto.id().toString()),
                        jsonPath("$.country").value(addressDto.country()),
                        jsonPath("$.city").value(addressDto.city()),
                        jsonPath("$.postalCode").value(addressDto.postalCode()),
                        jsonPath("$.street").value(addressDto.street()),
                        jsonPath("$.streetNumber").value(addressDto.streetNumber()),
                        jsonPath("$.additionalDetails").value(addressDto.additionalDetails())
                );
    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnStatusNotFound_WhenRestaurantNotExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when
        when(service.getAddressByRestaurantId(id)).thenThrow(new ObjectNotFoundException(id, Restaurant.class));

        // test
        mockMvc.perform(get("/api/v1/addresses?restaurantId=" + id))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Restaurant and id " + id + " cannot be found"));
    }

    @Test
    void testGetAddressByRestaurantId_ShouldReturnStatusOkAndAddressDto_WhenAddressWithValidRestaurantIdExist() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        AddressDto addressDto = new AddressDto(id,
                "Poland",
                "City",
                "59-900",
                "Street",
                "15B",
                "");

        // when
        when(service.getAddressByRestaurantId(id)).thenReturn(addressDto);

        // test
        mockMvc.perform(get("/api/v1/addresses?restaurantId=" + id))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(addressDto.id().toString()),
                        jsonPath("$.country").value(addressDto.country()),
                        jsonPath("$.city").value(addressDto.city()),
                        jsonPath("$.postalCode").value(addressDto.postalCode()),
                        jsonPath("$.street").value(addressDto.street()),
                        jsonPath("$.streetNumber").value(addressDto.streetNumber()),
                        jsonPath("$.additionalDetails").value(addressDto.additionalDetails())
                );
    }

    @Test
    void testCreateNewAddressAndUpdateAddress_ShouldReturnStatusCreated_WhenProvidingValidData() throws Exception {
        // given
        UUID restaurantId = UUID.fromString("41d35b7d-e4cb-4687-b56c-b4b9eb588241");
        UUID addressId = UUID.fromString("4e3dbfd4-ada7-4273-863c-d018f402917a");

        AddressDto addressDto = new AddressDto(
                addressId,
                "Poland",
                "City",
                "59-900",
                "Street",
                "15B",
                ""
        );

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

        String contentResponse = """
                {
                "id":  "4e3dbfd4-ada7-4273-863c-d018f402917a",
                "country": "Poland",
                "city": "City",
                "postalCode": "59-900",
                "street": "Street",
                "streetNumber": "15B",
                "additionalDetails": ""
                }
                """;
        // when
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        when(service.saveNewAddress(any(NewAddressDto.class))).thenReturn(addressDto);
        when(service.updateAddress(any(UUID.class), any(NewAddressDto.class))).thenReturn(addressDto);

        // test
        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse));

        mockMvc.perform(put("/api/v1/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse));
    }

    @Test
    void testCreateNewAddressAndUpdateAddress_ShouldReturnErrorMessages_WhenProvidingInvalidJson() throws Exception {
        // given
        String content = """
                {
                "country": "",
                "city": "",
                "postalCode": "",
                "street": "",
                "streetNumber": "",
                "additionalDetails": "",
                "restaurantId": ""
                }
                """;

        // when
        when(service.saveNewAddress(any(NewAddressDto.class))).thenReturn(any(AddressDto.class));

        // test
        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Country cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("City cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Postal code cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street number cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );

        mockMvc.perform(put("/api/v1/addresses/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Country cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("City cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Postal code cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Street number cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );
    }

    @Test
    void testDeleteAddress_ShouldReturnStatusNoContent_WhenAddressExist() throws Exception {
        // test
        mockMvc.perform(delete("/api/v1/addresses/" + UUID.randomUUID()))
                .andExpectAll(status().isNoContent());
    }

    @Test
    void testDeleteAddress_ShouldReturnStatusNotFound_WhenNoAddress() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        // when
        doThrow(new ObjectNotFoundException(id, Restaurant.class)).when(service).deleteAddress(id);
        // test
        mockMvc.perform(delete("/api/v1/addresses/" + id))
                .andExpectAll(status().isNotFound());
    }

}