package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return businessHourRepository.findAll().stream()
                .map(businessHourMapper::toDto)
                .toList();
    }

    public BusinessHourDto getBusinessHourBy(UUID id) {
        return businessHourRepository.findById(id)
                .map(businessHourMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, BusinessHour.class));
    }

    public List<BusinessHourDto> getBusinessHoursByRestaurantId(UUID restaurantId) {
        return businessHourRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(businessHourMapper::toDto)
                .toList();
    }

    public BusinessHourDto saveNewBusinessHour(NewBusinessHourDto newBusinessHourDto) {
        BusinessHour savedBusinessHour = businessHourRepository
                .save(businessHourMapper.dtoToBusinessHour(newBusinessHourDto));
        return businessHourMapper.toDto(savedBusinessHour);
    }

    public BusinessHourDto updateBusinessHour(UUID id, NewBusinessHourDto newBusinessHourDto) {
        BusinessHour savedBusinessHour = businessHourRepository
                .save(businessHourMapper.dtoToBusinessHour(newBusinessHourDto, id));
        return businessHourMapper.toDto(savedBusinessHour);
    }

    public void deleteBusinessHour(UUID id) {
        businessHourRepository.delete(businessHourMapper.dtoToBusinessHour(id));
    }

}
