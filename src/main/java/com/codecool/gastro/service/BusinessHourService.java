package com.codecool.gastro.service;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<BusinessHourDto> getBusinessHoursByRestaurantId(UUID restaurantId) {
        return businessHourRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(businessHourMapper::toDto)
                .toList();
    }

    public BusinessHourDto updateBusinessHour(UUID id, NewBusinessHourDto newBusinessHourDto) {
        BusinessHour updatedBusinessHour = businessHourRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, BusinessHour.class));

        businessHourMapper.updateBusinessHourFromDto(newBusinessHourDto, updatedBusinessHour);
        return businessHourMapper.toDto(businessHourRepository.save(updatedBusinessHour));
    }

    public void deleteBusinessHour(UUID id) {
        businessHourRepository.delete(businessHourMapper.dtoToBusinessHour(id));
    }

}
