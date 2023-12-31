package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.EditCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.projection.DetailedCustomerProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CustomerMapperTest {
    CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerId = UUID.fromString("71a4b2a2-52e3-4411-8daf-c00b3c9ca3ea");

        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");
        customer.setSubmissionTime(LocalDate.of(2012, 12, 12));
        customer.setPassword("PW");
    }

    @Test
    void testToDto_ShouldMapCustomerToDto_WhenCalled() {
        // then
        CustomerDto customerDto = mapper.toDto(customer);

        // test
        assertEquals(customerDto.id(), customer.getId());
        assertEquals(customerDto.name(), customer.getName());
        assertEquals(customerDto.surname(), customer.getSurname());
        assertEquals(customerDto.email(), customer.getEmail());
        assertEquals(customerDto.submissionTime(), customer.getSubmissionTime());
    }

    @Test
    void testToDetailedDto_ShouldMapCustomerToDetailedDto_WhenCalled() {
        // given
        UUID restaurantId = UUID.randomUUID();
        UUID ownershipId = UUID.randomUUID();

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        DetailedCustomerProjection projection = factory.createProjection(DetailedCustomerProjection.class);

        projection.setId(customerId);
        projection.setName(customer.getName());
        projection.setSurname(customer.getSurname());
        projection.setEmail(customer.getEmail());
        projection.setSubmissionTime(customer.getSubmissionTime());
        projection.setRestaurants(new UUID[]{restaurantId});
        projection.setOwnershipId(ownershipId);

        // then
        DetailedCustomerDto detailedCustomerDto = mapper.toDetailedDto(projection);

        // test
        assertEquals(detailedCustomerDto.id(), projection.getId());
        assertEquals(detailedCustomerDto.name(), projection.getName());
        assertEquals(detailedCustomerDto.surname(), projection.getSurname());
        assertEquals(detailedCustomerDto.email(), projection.getEmail());
        assertEquals(detailedCustomerDto.submissionTime(), projection.getSubmissionTime());
        assertEquals(detailedCustomerDto.restaurants()[0], projection.getRestaurants()[0]);
        assertEquals(detailedCustomerDto.ownershipId(), projection.getOwnershipId());
    }

    @Test
    void testDtoToCustomer_ShouldMapCustomerDto_WhenProvidingDto() {
        // given
        NewCustomerDto newCustomerDto = new NewCustomerDto(
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getPassword()
        );

        // then
        Customer customerOne = mapper.dtoToCustomer(newCustomerDto);

        // test
        assertEquals(customerOne.getName(), customer.getName());
        assertEquals(customerOne.getSurname(), customer.getSurname());
        assertEquals(customerOne.getEmail(), customer.getEmail());
        assertEquals(customerOne.getPassword(), customer.getPassword());
        assertEquals(customerOne.getRestaurants().size(), 0);
    }

    @Test
    void testUpdateCustomerFromDto_ShouldUpdateFieldsAndLeavesRestWithSameValues_WhenCalled() {
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");

        EditCustomerDto editCustomerDto = new EditCustomerDto(
                "editedName",
                "editedSurname"
        );

        // when
        mapper.updateCustomerFromDto(editCustomerDto, customer);

        // then
        assertNotEquals(customer.getName(), "Name");
        assertNotEquals(customer.getSurname(), "Surname");
        assertEquals(customer.getEmail(), "Email@wp.pl");
    }
}
