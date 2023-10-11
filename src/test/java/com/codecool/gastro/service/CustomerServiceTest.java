package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.EditCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedCustomerProjection;
import com.codecool.gastro.service.exception.EmailNotFoundException;
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
    RestaurantRepository restaurantRepository;
    @Mock
    CustomerMapper mapper;
    @Mock
    PasswordEncoder encoder;

    private UUID customerId;
    private UUID restaurantId;
    private UUID ownershipId;
    private Restaurant restaurant;
    private Customer customer;
    private CustomerDto customerDto;
    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    DetailedCustomerProjection projection = factory.createProjection(DetailedCustomerProjection.class);
    private DetailedCustomerDto detailedCustomerDto;
    private NewCustomerDto newCustomerDto;
    private EditCustomerDto editCustomerDto;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        ownershipId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        customer = new Customer();
        customer.setId(customerId);
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");
        customer.setPassword("PW");
        customer.setSubmissionTime(LocalDate.of(1212, 12, 12));

        customerDto = new CustomerDto(
                customerId,
                "Name",
                "Surname",
                "Email@wp.pl",
                LocalDate.of(1212, 12, 12)
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

        editCustomerDto = new EditCustomerDto(
                "Name",
                "Surname"
        );
    }

    @Test
    void testGetDetailedCustomerById_ShouldReturnDetailedCustomer_WhenExist() {
        // when
        when(repository.findDetailedById(customerId)).thenReturn(Optional.of(projection));
        when(mapper.toDetailedDto(projection)).thenReturn(detailedCustomerDto);
        DetailedCustomerDto testedDetailedCustomerDto = service.getDetailedCustomerById(customerId);

        // then
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

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.getDetailedCustomerById(customerId));
        verify(repository, times(1)).findDetailedById(customerId);
    }

    @Captor
    ArgumentCaptor<Customer> captor;

    @Test
    void testSaveCustomer_ShouldSetSubmissionTimeAndPasswordAndReturnCustomerDto_WhenCalled() {
        // when
        when(mapper.dtoToCustomer(newCustomerDto)).thenReturn(customer);
        when(encoder.encode(anyString())).thenReturn("EnCoDeDpAsSwOrD");
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toDto(customer)).thenReturn(customerDto);
        CustomerDto testedCustomerDto = service.saveCustomer(newCustomerDto);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getPassword(), "EnCoDeDpAsSwOrD");
        assertNotEquals(captor.getValue().getSubmissionTime(), LocalDate.of(1212, 12, 12));
        assertEquals(testedCustomerDto.name(), newCustomerDto.name());
        assertEquals(testedCustomerDto.surname(), newCustomerDto.surname());
        assertEquals(testedCustomerDto.email(), newCustomerDto.email());
        verify(mapper, times(1)).dtoToCustomer(newCustomerDto);
        verify(mapper, times(1)).toDto(customer);
    }

    @Test
    void testUpdateCustomer_ShouldReturnUpdatedCustomerDto_WhenExist() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(customer);
        when(mapper.toDto(customer)).thenReturn(customerDto);
        CustomerDto testedCustomerDto = service.updateCustomer(customerId, editCustomerDto);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(testedCustomerDto.id(), customerId);
        assertEquals(testedCustomerDto.name(), newCustomerDto.name());
        assertEquals(testedCustomerDto.surname(), newCustomerDto.surname());
        assertEquals(testedCustomerDto.email(), newCustomerDto.email());
        assertEquals(testedCustomerDto.submissionTime(), captor.getValue().getSubmissionTime());
        verify(repository, times(1)).findById(customerId);
        verify(mapper, times(1)).toDto(customer);
    }

    @Test
    void testUpdateCustomer_ShouldThrowObjectNotFoundException_WhenIsDeletedOrNoCustomer() {
        // when
        when(repository.findById(customerId)).thenThrow(ObjectNotFoundException.class);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.updateCustomer(customerId, editCustomerDto));
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testSoftDelete_ShouldObfuscateCustomer_WhenExist() {
        // given
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");

        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        service.softDelete(customerId);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getSurname(), "*" .repeat(customer.getSurname().length()));
        assertNotEquals(captor.getValue().getEmail(), "Email@wp.pl");
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testSoftDelete_ShouldThrowObjectNotFoundException_WhenNoCustomerOrDeleted() {
        // when
        when(repository.findById(customerId)).thenThrow(ObjectNotFoundException.class);

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.softDelete(customerId));
    }

    @Test
    void testGetCustomerByEmail_ShouldReturnCustomerDto_WhenExist() {
        // when
        when(repository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(mapper.toDto(customer)).thenReturn(customerDto);
        CustomerDto testedCustomerDto = service.getCustomerByEmail(customer.getEmail());

        // then
        assertEquals(customer.getId(), testedCustomerDto.id());
        assertEquals(customer.getName(), testedCustomerDto.name());
        assertEquals(customer.getSurname(), testedCustomerDto.surname());
        assertEquals(customer.getEmail(), testedCustomerDto.email());
        assertEquals(customer.getSubmissionTime(), testedCustomerDto.submissionTime());
    }

    @Test
    void testSoftDelete_ShouldThrowEmailNotFoundException_WhenNoCustomerOrDeleted() {
        // when
        when(repository.findByEmail(customer.getEmail())).thenThrow(EmailNotFoundException.class);

        // then
        assertThrows(EmailNotFoundException.class, () -> service.getCustomerByEmail(customer.getEmail()));
    }

    @Test
    void testAssignRestaurantToCustomer_ShouldSaveCustomerWithAssignedRestaurant_WhenExist() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        service.assignRestaurantToCustomer(customerId, restaurantId);

        // then
        verify(repository, times(1)).save(captor.capture());
        assertTrue(captor.getValue().getRestaurants().contains(restaurant));
    }

    @Test
    void testAssignRestaurantToCustomer_ShouldThrowObjectNotFoundException_WhenNoCustomer() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.assignRestaurantToCustomer(customerId, restaurantId));
    }

    @Test
    void testAssignRestaurantToCustomer_ShouldThrowObjectNotFoundException_WhenNoRestaurant() {
        // when
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> service.assignRestaurantToCustomer(customerId, restaurantId));
    }
}
