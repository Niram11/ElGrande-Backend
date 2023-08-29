package com.codecool.gastro.service;

import com.codecool.gastro.controler.dto.businesshour.BusinessHourDTO;
import com.codecool.gastro.controler.dto.businesshour.NewBusinessHourDTO;
import com.codecool.gastro.controler.dto.restaurant.NewRestaurantDTO;
import com.codecool.gastro.controler.dto.restaurant.RestaurantDTO;
import com.codecool.gastro.mapper.BusinessHourMapper;
import com.codecool.gastro.mapper.RestaurantMapper;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public BusinessHourDTO saveNewBusinessHour(NewBusinessHourDTO newBusinessHourDTO) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.DTOToBusinessHour(newBusinessHourDTO));
        return businessHourMapper.businessHourToDTO(savedBusinessHour);
    }

    public BusinessHourDTO updateBusinessHour(UUID id, NewBusinessHourDTO newBusinessHourDTO) {
        BusinessHour savedBusinessHour = businessHourRepository.save(businessHourMapper.DTOToBusinessHour(newBusinessHourDTO, id));
        return businessHourMapper.businessHourToDTO(savedBusinessHour);
    }
}
