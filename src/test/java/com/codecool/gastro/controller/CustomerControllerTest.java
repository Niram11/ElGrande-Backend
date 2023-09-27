package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.service.CustomerService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;

    private UUID customerId;
    private UUID restaurantId;
    private UUID ownershipId;

    @BeforeEach
    void setUp() {
        customerId = UUID.fromString("2b92aa17-b11a-4ef7-afce-bd37fa1d8b7b");
        restaurantId = UUID.fromString("5d05e324-757b-421a-9a47-1f60a7f57120");
        ownershipId = UUID.fromString("dd3895ce-539a-48f7-984d-8adfc3da6565");
    }

    @Test
    void testGetCustomerById_ShouldReturnStatusOkAndCustomerDto_WhenCustomerExist() throws Exception {
        // when
        RestaurantDto restaurantDto = new RestaurantDto(
                restaurantId,
                "Name",
                "Desc",
                "Website.pl",
                123123123,
                "Email@gmail.com"
        );

        CustomerDto customerDto = new CustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2001, 12, 12),
                Set.of(restaurantDto)
        );

        String contentRespond = """
                {
                    "id": "2b92aa17-b11a-4ef7-afce-bd37fa1d8b7b",
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "submissionTime": "2001-12-12",
                    "restaurants" : [
                        {
                            "id": "5d05e324-757b-421a-9a47-1f60a7f57120",
                            "name": "Name",
                            "description": "Desc",
                            "website": "Website.pl",
                            "contactNumber": 123123123,
                            "contactEmail": "Email@gmail.com"
                        }
                    ]
                }
                """;

        // when
        when(service.getCustomerById(customerId)).thenReturn(customerDto);

        // test
        mockMvc.perform(get("/api/v1/customers/" + customerId))
                .andExpectAll(status().isOk(),
                        content().json(contentRespond));
    }

    @Test
    void testGetCustomerById_ShouldReturnStatusNotFoundAndErrorMessage_WhenNoCustomer() throws Exception {
        // when
        doThrow(new ObjectNotFoundException(customerId, Customer.class)).when(service).getCustomerById(customerId);

        // test
        mockMvc.perform(get("/api/v1/customers/" + customerId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Customer and id " + customerId + " cannot be found")
                );
    }

    @Test
    void testGetDetailedCustomerById_ShouldReturnStatusOkAndDetailedCustomerDto_WhenCustomerExist() throws Exception {
        // given
        DetailedCustomerDto detailedCustomerDto = new DetailedCustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2001, 12, 12),
                new UUID[]{restaurantId},
                ownershipId
        );

        String contentResponse = """
                {
                    "id": "2b92aa17-b11a-4ef7-afce-bd37fa1d8b7b",
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "submissionTime": "2001-12-12",
                    "restaurants": [
                         "5d05e324-757b-421a-9a47-1f60a7f57120"
                    ],
                    "ownershipId": "dd3895ce-539a-48f7-984d-8adfc3da6565"
                }
                """;

        // when
        when(service.getDetailedCustomerById(customerId)).thenReturn(detailedCustomerDto);

        // test
        mockMvc.perform(get("/api/v1/customers/" + customerId + "/details"))
                .andExpectAll(status().isOk(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testGetDetailedCustomerById_ShouldReturnStatusNotFoundAndErrorMessage_WhenNoCustomer() throws Exception {
        // when
        doThrow(new ObjectNotFoundException(customerId, Customer.class)).when(service).getDetailedCustomerById(customerId);

        // test
        mockMvc.perform(get("/api/v1/customers/" + customerId + "/details"))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Customer and id " + customerId + " cannot be found")
                );
    }

    @Test
    void testUpdateCustomer_ShouldReturnStatusCreatedAndCustomerDto_WhenProvidingValidDataWithoutRestaurants() throws Exception {
        // given
        NewCustomerDto newCustomerDto = new NewCustomerDto(
                "Name",
                "Surname",
                "Email@wp.pl",
                "PW"
        );

        CustomerDto customerDto = new CustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2012, 12, 12),
                Set.of()
        );

        String contentRequest = """
                {
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "password": "PW"
                }
                """;
        String contentRespond = """
                {
                    "id": "2b92aa17-b11a-4ef7-afce-bd37fa1d8b7b",
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "submissionTime": "2012-12-12",
                    "restaurants": []
                }
                """;
        // when
        when(repository.findByEmail(newCustomerDto.email())).thenReturn(Optional.empty());
        when(service.saveCustomer(newCustomerDto)).thenReturn(customerDto);
        when(service.updateCustomer(customerId, newCustomerDto)).thenReturn(customerDto);

        // test
        mockMvc.perform(put("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentRespond)
                );
    }

    @Test
    void testCreateNewCustomerAndUpdateCustomer_ShouldReturnStatusCreatedAndCustomerDto_WhenProvidingValidDataWithRestaurants() throws Exception {
        // given
        RestaurantDto restaurantDto = new RestaurantDto(
                restaurantId,
                null,
                null,
                null,
                null,
                null
        );

        NewCustomerDto newCustomerDto = new NewCustomerDto(
                "Name",
                "Surname",
                "Email@wp.pl",
                "PW"
        );

        CustomerDto customerDto = new CustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2012, 12, 12),
                Set.of(restaurantDto)
        );

        String contentRequest = """
                {
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "password": "PW"
                }
                """;
        String contentRespond = """
                {
                    "id": "2b92aa17-b11a-4ef7-afce-bd37fa1d8b7b",
                    "name": "Name",
                    "surname": "Surname",
                    "email": "Email@wp.pl",
                    "submissionTime": "2012-12-12",
                    "restaurants": [
                        {
                            "id": "5d05e324-757b-421a-9a47-1f60a7f57120",
                            "name": null,
                            "description": null,
                            "website": null,
                            "contactNumber": null,
                            "contactEmail": null
                        }
                    ]
                }
                """;
        // when
        when(repository.findByEmail(newCustomerDto.email())).thenReturn(Optional.empty());
        when(service.saveCustomer(newCustomerDto)).thenReturn(customerDto);
        when(service.updateCustomer(customerId, newCustomerDto)).thenReturn(customerDto);

        // test
        mockMvc.perform(put("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentRespond)
                );
    }

    @Test
    void testUpdateCustomer_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidData() throws Exception {
        // given
        String contentRequest = """
                {
                    "name": "",
                    "surname": "",
                    "email": "",
                    "password": "",
                    "restaurants": []
                }
                """;

        // test
        mockMvc.perform(put("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Name cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Surname cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Email cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Password cannot be empty"))
                );

        // given
        contentRequest = """
                {
                    "name": "Name",
                    "surname": "Surname",
                    "email": "email.pl",
                    "password": "PW",
                    "restaurants": []
                }
                """;

        // then
        mockMvc.perform(put("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Invalid email"))
                );
    }

    @Test
    void testSoftDeleteCustomer_ShouldReturnStatusNoContent_WhenCustomerExist() throws Exception {
        // test
        mockMvc.perform(delete("/api/v1/customers/" + customerId))
                .andExpectAll(status().isNoContent());
    }

    @Test
    void testSoftDeleteCustomer_ShouldReturnStatusNotFound_WhenNoCustomer() throws Exception {
        // when
        doThrow(new ObjectNotFoundException(customerId, Customer.class)).when(service).softDelete(customerId);

        // test
        mockMvc.perform(delete("/api/v1/customers/" + customerId))
                .andExpectAll(status().isNotFound());
    }
}
