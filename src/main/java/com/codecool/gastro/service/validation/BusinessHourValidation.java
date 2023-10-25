package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.UUID;

public class BusinessHourValidation implements Validation<UUID>{
    private final BusinessHourRepository businessHourRepository;

    public BusinessHourValidation(BusinessHourRepository businessHourRepository) {
        this.businessHourRepository = businessHourRepository;
    }

    @Override
    public void validateUpdate(UUID id) {
        businessHourRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, BusinessHour.class));

    }
}
