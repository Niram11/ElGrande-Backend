package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);

    Customer dtoToCustomer(CustomerDto customerDto, UUID id);
}
