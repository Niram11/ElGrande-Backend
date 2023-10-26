package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.validation.BusinessHourValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
    private final BusinessHourMapper businessHourMapper;
    private final BusinessHourValidation validation;

    public BusinessHourService(BusinessHourRepository businessHourRepository, BusinessHourMapper businessHourMapper,
                               BusinessHourValidation validation) {
        this.businessHourRepository = businessHourRepository;
        this.businessHourMapper = businessHourMapper;
        this.validation = validation;
    }

    public List<BusinessHourDto> getBusinessHoursByRestaurantId(UUID restaurantId) {
        return businessHourRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(businessHourMapper::toDto)
                .toList();
    }

    public BusinessHourDto updateBusinessHour(UUID id, NewBusinessHourDto newBusinessHourDto) {
        validation.validateEntityById(id);
        BusinessHour updatedBusinessHour = businessHourRepository.findById(id).get();
        businessHourMapper.updateBusinessHourFromDto(newBusinessHourDto, updatedBusinessHour);
        return businessHourMapper.toDto(businessHourRepository.save(updatedBusinessHour));
    }

    public void deleteBusinessHour(UUID id) {
        businessHourRepository.delete(businessHourMapper.dtoToBusinessHour(id));
    }

}
