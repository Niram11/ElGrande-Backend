package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.PromotedLocalRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PromotedLocalValidation implements Validation<UUID, PromotedLocal> {
    private final PromotedLocalRepository promotedLocalRepository;

    public PromotedLocalValidation(PromotedLocalRepository promotedLocalRepository) {
        this.promotedLocalRepository = promotedLocalRepository;
    }

    @Override
    public PromotedLocal validateEntityById(UUID id) {
        return promotedLocalRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, PromotedLocal.class));
    }
}
