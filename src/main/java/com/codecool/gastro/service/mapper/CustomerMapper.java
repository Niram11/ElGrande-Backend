package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(NewCustomerDto customerDto);

    Customer dtoToCustomer(NewCustomerDto customerDto, UUID id);
}