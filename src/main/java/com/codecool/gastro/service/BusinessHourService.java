package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
    private final BusinessHourMapper businessHourMapper;

    public BusinessHourService(BusinessHourRepository businessHourRepository, BusinessHourMapper businessHourMapper) {
        this.businessHourRepository = businessHourRepository;
        this.businessHourMapper = businessHourMapper;
    }

    public List<BusinessHourDTO> getBusinessHours() {
        return businessHourRepository.findAllBy().stream()
                .map(businessHourMapper::businessHourToDTO).toList();
    }

    public BusinessHourDTO getBusinessHourById(UUID id) {
        return businessHourRepository.findById(id)
                .map(businessHourMapper::businessHourToDTO)
                .orElseThrow(() -> new EntityNotFoundException(id, BusinessHour.class));
    }

    public BusinessHourDTO saveNewBusinessHour(NewBusinessHourDTO newBusinessHourDTO) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.DTOToBusinessHour(newBusinessHourDTO));
        return businessHourMapper.businessHourToDTO(savedBusinessHour);
    }

    public BusinessHourDTO updateBusinessHour(UUID id, NewBusinessHourDTO newBusinessHourDTO) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.DTOToBusinessHour(newBusinessHourDTO, id));
        return businessHourMapper.businessHourToDTO(savedBusinessHour);
    }

    public void deleteBusinessHour(UUID id) {
        BusinessHour deletedBusinessHour = businessHourMapper.DTOToBusinessHour(id);
        businessHourRepository.delete(deletedBusinessHour);
    }

}
