package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    @Mapping(target = "uuid", source = "customer.uuid")
    @Mapping(target = "forename", source = "customer.forename")
    @Mapping(target = "surname", source = "customer.surname")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(target = "passwordHash", source = "customer.passwordHash")
    CustomerDto customerToDto(Customer customer);

    @Mapping(source = "customerDto.forename", target = "forename")
    @Mapping(source = "customerDto.surname", target = "surname")
    @Mapping(source = "customerDto.email", target = "email")
    @Mapping(source = "customerDto.password", target = "passwordHash")
    Customer DtoToCustomer(CustomerDto customerDto);

    @Mapping(source = "customerDto.forename", target = "forename")
    @Mapping(source = "customerDto.surname", target = "surname")
    @Mapping(source = "customerDto.email", target = "email")
    @Mapping(source = "customerDto.password", target = "passwordHash")
    Customer DtoToCustomer(CustomerDto customerDto, UUID uuid);
}
