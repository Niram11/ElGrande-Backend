package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.OwnershipRepository;
import com.codecool.gastro.repository.entity.Ownership;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnershipValidation implements Validation<UUID, Ownership> {
    private final OwnershipRepository ownershipRepository;

    public OwnershipValidation(OwnershipRepository ownershipRepository) {
        this.ownershipRepository = ownershipRepository;
    }

    @Override
    public Ownership validateEntityById(UUID id) {
        return ownershipRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Ownership.class));
    }
}
