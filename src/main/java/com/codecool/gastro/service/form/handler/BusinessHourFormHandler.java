package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.BusinessHourRepository;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessHourFormHandler implements FormHandler<BusinessHour> {
    private final BusinessHourMapper businessHourMapper;
    private final BusinessHourRepository businessHourRepository;

    public BusinessHourFormHandler(BusinessHourMapper businessHourMapper, BusinessHourRepository businessHourRepository) {
        this.businessHourMapper = businessHourMapper;
        this.businessHourRepository = businessHourRepository;
    }

    @Override
    public void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant) {
        List<BusinessHour> businessHourList = formDto.businessHour().stream()
                .map(bh -> {
                    BusinessHour businessHour = businessHourMapper.dtoToBusinessHour(bh);
                    businessHour.setRestaurant(restaurant);
                    return businessHour;
                })
                .toList();

        businessHourRepository.saveAll(businessHourList);
    }
}
