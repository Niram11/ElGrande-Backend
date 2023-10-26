package com.codecool.gastro.service.validation;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.repository.OwnershipRepository;
import com.codecool.gastro.repository.entity.Ownership;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OwnershipValidation implements Validation<NewOwnershipDto>{
    private final OwnershipRepository ownershipRepository;

    public OwnershipValidation(OwnershipRepository ownershipRepository) {
        this.ownershipRepository = ownershipRepository;
    }

    @Override
    public void validateEntityById(NewOwnershipDto newOwnershipDto) {
        ownershipRepository.findById(newOwnershipDto.customerId())
                .orElseThrow(() -> new ObjectNotFoundException(newOwnershipDto.customerId(), Ownership.class));
    }
}
