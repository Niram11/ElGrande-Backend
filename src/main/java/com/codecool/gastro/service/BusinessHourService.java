package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
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

    public List<BusinessHourDto> getBusinessHours() {
        return businessHourRepository.findAllBy().stream()
                .map(businessHourMapper::businessHourToDto)
                .toList();
    }

    public BusinessHourDto getBusinessHourById(UUID id) {
        return businessHourRepository.findById(id)
                .map(businessHourMapper::businessHourToDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, BusinessHour.class));
    }

    public BusinessHourDto saveNewBusinessHour(NewBusinessHourDto newBusinessHourDto) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.dtoToBusinessHour(newBusinessHourDto));
        return businessHourMapper.businessHourToDto(savedBusinessHour);
    }

    public BusinessHourDto updateBusinessHour(UUID id, NewBusinessHourDto newBusinessHourDto) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.dtoToBusinessHour(newBusinessHourDto, id));
        return businessHourMapper.businessHourToDto(savedBusinessHour);
    }

    public void deleteBusinessHour(UUID id) {
        BusinessHour deletedBusinessHour = businessHourMapper.dtoToBusinessHour(id);
        businessHourRepository.delete(deletedBusinessHour);
    }

}
