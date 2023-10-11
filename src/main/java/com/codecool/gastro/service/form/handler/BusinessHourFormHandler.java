package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.businesshour.NewBusinessHourDto;
import com.codecool.gastro.repository.entity.BusinessHour;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.form.handler.FormHandler;
import com.codecool.gastro.service.mapper.BusinessHourMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessHourFormHandler implements FormHandler<BusinessHour, NewBusinessHourDto> {
    private final BusinessHourMapper businessHourMapper;

    public BusinessHourFormHandler(BusinessHourMapper businessHourMapper) {
        this.businessHourMapper = businessHourMapper;;
    }

    @Override
    public BusinessHour handleDto(NewBusinessHourDto newBusinessHourDto, Restaurant restaurant) {
        BusinessHour businessHour = businessHourMapper.dtoToBusinessHour(newBusinessHourDto);
        businessHour.setRestaurant(restaurant);

        return businessHour;
    }
}
