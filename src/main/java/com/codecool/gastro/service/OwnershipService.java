package com.codecool.gastro.service;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.repository.OwnershipRepository;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ownership;
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

    public OwnershipService(OwnershipMapper ownershipMapper, OwnershipRepository ownershipRepository,
                            OwnershipValidation validation) {
        this.ownershipMapper = ownershipMapper;
        this.ownershipRepository = ownershipRepository;
        this.validation = validation;
    }

    public OwnershipDto getOwnershipById(UUID id) {
        return ownershipRepository.findById(id)
                .map(ownershipMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, DishCategory.class));
    }

    public OwnershipDto saveOwnership(NewOwnershipDto newOwnershipDto) {
        Ownership savedOwnership = ownershipRepository.save(ownershipMapper.dtoToOwnership(newOwnershipDto));
        return ownershipMapper.toDto(savedOwnership);
    }

    public OwnershipDto updateOwnership(UUID id, NewOwnershipDto newOwnershipDto) {
        Ownership updatedOwnership = validation.validateEntityById(newOwnershipDto);
        ownershipMapper.updateOwnershipFromDto(newOwnershipDto, updatedOwnership);
        return ownershipMapper.toDto(ownershipRepository.save(updatedOwnership));
    }

    public void deleteOwnership(UUID id) {
        ownershipRepository.delete(ownershipMapper.dtoToOwnership(id));
    }
}
