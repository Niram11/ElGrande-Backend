package com.codecool.gastro.service;

import com.codecool.gastro.dto.ownership.EditOwnershipDto;
import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.OwnershipRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.*;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.OwnershipMapper;
import com.codecool.gastro.service.validation.OwnershipValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnershipService {
    private final OwnershipMapper ownershipMapper;
    private final OwnershipRepository ownershipRepository;
    private final OwnershipValidation validation;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    public OwnershipService(OwnershipMapper ownershipMapper, OwnershipRepository ownershipRepository,
                            OwnershipValidation validation, CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.ownershipMapper = ownershipMapper;
        this.ownershipRepository = ownershipRepository;
        this.validation = validation;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public OwnershipDto getOwnershipById(UUID id) {
        return ownershipRepository.findById(id)
                .map(ownershipMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, DishCategory.class));
    }

    public OwnershipDto saveOwnership(NewOwnershipDto newOwnershipDto) {
        validation.validateOwnershipByCustomerId(newOwnershipDto.customerId());
        applyNewRoleToCustomer(newOwnershipDto.customerId());
        Ownership savedOwnership = ownershipRepository.save(ownershipMapper.dtoToOwnership(newOwnershipDto));
        return ownershipMapper.toDto(savedOwnership);
    }

    public OwnershipDto updateOwnership(UUID id, EditOwnershipDto editOwnershipDto) {
        Ownership updatedOwnership = validation.validateEntityById(id);
        editOwnershipDto.restaurantsId()
                .forEach(restaurantId -> restaurantRepository.findById(restaurantId)
                        .ifPresent(updatedOwnership::assignRestaurantToOwnership)
                );
        return ownershipMapper.toDto(ownershipRepository.save(updatedOwnership));
    }

    public void deleteOwnership(UUID id) {
        ownershipRepository.delete(ownershipMapper.dtoToOwnership(id));
    }

    private void applyNewRoleToCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class));

        Role role = new Role();
        role.setRole(CustomerRole.ROLE_OWNER);

        customer.assignRole(role);
        customerRepository.save(customer);
    }
}
