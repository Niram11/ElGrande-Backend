package com.codecool.gastro.service;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.repository.PromotedLocalRepository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.PromotedLocalMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PromotedLocalService {
    private final PromotedLocalRepository promotedLocalRepository;
    private final PromotedLocalMapper promotedLocalMapper;

    public PromotedLocalService(PromotedLocalRepository promotedLocalRepository, PromotedLocalMapper promotedLocalMapper) {
        this.promotedLocalRepository = promotedLocalRepository;
        this.promotedLocalMapper = promotedLocalMapper;
    }

    public List<PromotedLocalDto> getPromotedLocals() {
        return promotedLocalRepository.findAllBy().stream()
                .map(promotedLocalMapper::promotedLocalToDto).toList();
    }

    public PromotedLocalDto getPromotedLocalBy(UUID id) {
        return promotedLocalRepository.findById(id)
                .map(promotedLocalMapper::promotedLocalToDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, PromotedLocal.class));
    }

    public PromotedLocalDto saveNewPromotedLocal(NewPromotedLocalDto newPromotedLocalDto) {
        PromotedLocal savedPromotedLocal = promotedLocalRepository.save(promotedLocalMapper.dtoToPromotedLocal(newPromotedLocalDto));
        return promotedLocalMapper.promotedLocalToDto(savedPromotedLocal);
    }

    public PromotedLocalDto updatePromotedLocal(UUID id, NewPromotedLocalDto newPromotedLocalDto) {
        PromotedLocal updatedPromotedLocal = promotedLocalRepository.save(promotedLocalMapper.dtoToPromotedLocal(newPromotedLocalDto, id));
        return promotedLocalMapper.promotedLocalToDto(updatedPromotedLocal);
    }

    public void deletePromotedLocal(UUID id) {
        PromotedLocal deletedPromotedLocal = promotedLocalMapper.dtoToPromotedLocal(id);
        promotedLocalRepository.delete(deletedPromotedLocal);
    }

}
