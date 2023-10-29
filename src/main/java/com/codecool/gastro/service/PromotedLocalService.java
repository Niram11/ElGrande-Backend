package com.codecool.gastro.service;

import com.codecool.gastro.dto.promotedlocal.EditPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.PromotedLocalRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.service.mapper.PromotedLocalMapper;
import com.codecool.gastro.service.validation.PromotedLocalValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PromotedLocalService {
    private final PromotedLocalRepository promotedLocalRepository;
    private final PromotedLocalMapper promotedLocalMapper;
    private final PromotedLocalValidation validation;

    public PromotedLocalService(PromotedLocalRepository promotedLocalRepository, PromotedLocalMapper promotedLocalMapper,
                                PromotedLocalValidation validation) {
        this.promotedLocalRepository = promotedLocalRepository;
        this.promotedLocalMapper = promotedLocalMapper;
        this.validation = validation;
    }

    public List<PromotedLocalDto> getPromotedLocals() {
        return promotedLocalRepository.findAll()
                .stream()
                .map(promotedLocalMapper::toDto)
                .toList();
    }

    public PromotedLocalDto saveNewPromotedLocal(NewPromotedLocalDto newPromotedLocalDto) {
        PromotedLocal savedPromotedLocal = promotedLocalRepository.save(promotedLocalMapper.dtoToPromotedLocal(newPromotedLocalDto));
        return promotedLocalMapper.toDto(savedPromotedLocal);
    }

    public PromotedLocalDto updatePromotedLocal(UUID id, EditPromotedLocalDto editPromotedLocalDto) {
        validation.validateEntityById(id);
        //TODO: create query in repo(not now)
        PromotedLocal updatedPromotedLocal = promotedLocalRepository.findById(id).get();
        promotedLocalMapper.updatePromotedLocalFromDto(editPromotedLocalDto, updatedPromotedLocal);
        return promotedLocalMapper.toDto(promotedLocalRepository.save(updatedPromotedLocal));
    }

    public void deletePromotedLocal(UUID id) {
        promotedLocalRepository.delete(promotedLocalMapper.dtoToPromotedLocal(id));
    }
}