package com.codecool.gastro.service;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.repository.OwnershipRepository;
import com.codecool.gastro.repository.entity.DishCategory;
import com.codecool.gastro.repository.entity.Ownership;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.OwnershipMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OwnershipService {
    private final OwnershipMapper ownershipMapper;
    private final OwnershipRepository ownershipRepository;

    public OwnershipService(OwnershipMapper ownershipMapper, OwnershipRepository ownershipRepository) {
        this.ownershipMapper = ownershipMapper;
        this.ownershipRepository = ownershipRepository;
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
        Ownership updateOwnership = ownershipRepository.save(
                ownershipMapper.dtoToOwnership(newOwnershipDto, id));
        return ownershipMapper.toDto(updateOwnership);
    }

    public void deleteOwnership(UUID id) {
        ownershipRepository.delete(ownershipMapper.dtoToOwnership(id));
    }
}
