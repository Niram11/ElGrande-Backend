package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.projection.DetailedCustomerProjection;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    CustomerService service;
    @Mock
    CustomerRepository repository;
    @Mock
    CustomerMapper mapper;
    @Mock
    PasswordEncoder encoder;

    private UUID customerId;
    private Customer customer;
    private CustomerDto customerDto;
    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    DetailedCustomerProjection projection = factory.createProjection(DetailedCustomerProjection.class);
    private DetailedCustomerDto detailedCustomerDto;
    private NewCustomerDto newCustomerDto;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        UUID ownershipId = UUID.randomUUID();
        customer = new Customer();

        customerDto = new CustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2012, 12, 12),
                Set.of()
        );

        detailedCustomerDto = new DetailedCustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(2012, 12, 12),
                new UUID[]{},
                ownershipId
        );

        newCustomerDto = new NewCustomerDto(
                "Name",
                "Surname",
                "Email@wp.pl",
                "PW"
        );
    }

    @Test
    void testGetCustomerById_ShouldReturnCustomerDto_WhenExist() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.toDto(customer)).thenReturn(customerDto);

        // then
        CustomerDto testedCustomerDto = service.getCustomerById(customerId);

        // test
        assertEquals(testedCustomerDto.id(), customerDto.id());
        assertEquals(testedCustomerDto.name(), customerDto.name());
        assertEquals(testedCustomerDto.surname(), customerDto.surname());
        assertEquals(testedCustomerDto.email(), customerDto.email());
        assertEquals(testedCustomerDto.submissionTime(), customerDto.submissionTime());
        assertEquals(testedCustomerDto.restaurants(), customerDto.restaurants());
        verify(repository, times(1)).findById(customerId);
        verify(mapper, times(1)).toDto(customer);
    }

    @Test
    void testGetCustomerById_ShouldThrownObjectNotFoundException_WhenNoCustomer() {
        // when
        when(repository.findById(customerId)).thenThrow(ObjectNotFoundException.class);

        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getCustomerById(customerId));
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testGetDetailedCustomerById_ShouldReturnDetailedCustomer_WhenExist() {
        // when
        when(repository.findDetailedById(customerId)).thenReturn(Optional.of(projection));
        when(mapper.toDetailedDto(projection)).thenReturn(detailedCustomerDto);

        // then
        DetailedCustomerDto testedDetailedCustomerDto = service.getDetailedCustomerById(customerId);

        // test
        assertEquals(testedDetailedCustomerDto.id(), detailedCustomerDto.id());
        assertEquals(testedDetailedCustomerDto.name(), detailedCustomerDto.name());
        assertEquals(testedDetailedCustomerDto.surname(), detailedCustomerDto.surname());
        assertEquals(testedDetailedCustomerDto.email(), detailedCustomerDto.email());
        assertEquals(testedDetailedCustomerDto.submissionTime(), detailedCustomerDto.submissionTime());
        assertEquals(testedDetailedCustomerDto.restaurants(), detailedCustomerDto.restaurants());
        assertEquals(testedDetailedCustomerDto.ownershipId(), detailedCustomerDto.ownershipId());
        verify(repository, times(1)).findDetailedById(customerId);
        verify(mapper, times(1)).toDetailedDto(projection);
    }

    @Test
    void testGetDetailedCustomerById_ShouldThrowObjectNotFoundException_WhenNoCustomer() {
        // when
        when(repository.findDetailedById(customerId)).thenThrow(ObjectNotFoundException.class);

        // test
        assertThrows(ObjectNotFoundException.class, () -> service.getDetailedCustomerById(customerId));
        verify(repository, times(1)).findDetailedById(customerId);
    }

    @Captor
    ArgumentCaptor<Customer> captor;

    @Test
    void testSaveCustomer_ShouldReturnCustomerDtoWithSubmissionTime_WhenCalled() {
        // when
        when(mapper.dtoToCustomer(newCustomerDto)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toDto(customer)).thenReturn(customerDto);

        // then
        CustomerDto testedCustomerDto = service.saveCustomer(newCustomerDto);

        // test
        verify(repository, times(1)).save(captor.capture());
        assertEquals(customer.getSubmissionTime(), captor.getValue().getSubmissionTime());
        assertEquals(testedCustomerDto.name(), newCustomerDto.name());
        assertEquals(testedCustomerDto.surname(), newCustomerDto.surname());
        assertEquals(testedCustomerDto.email(), newCustomerDto.email());
        verify(mapper, times(1)).dtoToCustomer(newCustomerDto);
        verify(mapper, times(1)).toDto(customer);
    }

    @Test
    void testUpdateCustomer_ShouldReturnUpdatedCustomerDto_WhenIsNotDeleted() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.dtoToCustomer(customerId, newCustomerDto)).thenReturn(customer);
        when(mapper.toDto(customer)).thenReturn(customerDto);

        // then
        CustomerDto testedCustomerDto = service.updateCustomer(customerId, newCustomerDto);

        // test
        verify(repository, times(1)).save(captor.capture());
        assertEquals(testedCustomerDto.id(), customerId);
        assertEquals(testedCustomerDto.name(), newCustomerDto.name());
        assertEquals(testedCustomerDto.surname(), newCustomerDto.surname());
        assertEquals(testedCustomerDto.email(), newCustomerDto.email());
        assertNotEquals(testedCustomerDto.submissionTime(), captor.getValue().getSubmissionTime());
        verify(repository, times(1)).findById(customerId);
        verify(mapper, times(1)).dtoToCustomer(customerId, newCustomerDto);
        verify(mapper, times(1)).toDto(customer);
    }

    @Test
    void testUpdateCustomer_ShouldThrowObjectNotFoundException_WhenIsDeletedOrNoCustomer() {
        // when
        when(repository.findById(customerId)).thenThrow(ObjectNotFoundException.class);

        // test
        assertThrows(ObjectNotFoundException.class, () -> service.updateCustomer(customerId, newCustomerDto));
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testSoftDelete_ShouldObfuscateCustomer_WhenExist() {
        // given
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");

        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));

        // then
        service.softDelete(customerId);

        // test
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getSurname(), "*".repeat(customer.getSurname().length()));
        assertNotEquals(captor.getValue().getEmail(), "Email@wp.pl");
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testSoftDelete_ShouldThrowObjectNotFoundException_WhenNoCustomerOrDeleted() {
        // when
        when(repository.findById(customerId)).thenThrow(ObjectNotFoundException.class);

        // test
        assertThrows(ObjectNotFoundException.class, () -> service.softDelete(customerId));
    }

}
