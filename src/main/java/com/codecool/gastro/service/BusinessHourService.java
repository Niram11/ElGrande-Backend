package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.service.validation.BusinessHourValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
    private final BusinessHourMapper businessHourMapper;
    private final BusinessHourValidation businessHourValidation;

    public BusinessHourService(BusinessHourRepository businessHourRepository, BusinessHourMapper businessHourMapper,
                               BusinessHourValidation businessHourValidation) {
        this.businessHourRepository = businessHourRepository;
        this.businessHourMapper = businessHourMapper;
        this.businessHourValidation = businessHourValidation;
    }

    public List<BusinessHourDto> getBusinessHoursByRestaurantId(UUID restaurantId) {
        return businessHourRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(businessHourMapper::toDto)
                .toList();
    }

    public BusinessHourDto updateBusinessHour(UUID id, NewBusinessHourDto newBusinessHourDto) {
        BusinessHour updatedBusinessHour = businessHourValidation.validateEntityById(id);
        businessHourMapper.updateBusinessHourFromDto(newBusinessHourDto, updatedBusinessHour);
        return businessHourMapper.toDto(businessHourRepository.save(updatedBusinessHour));
    }

    public void deleteBusinessHour(UUID id) {
        businessHourRepository.delete(businessHourMapper.dtoToBusinessHour(id));
    }

}
