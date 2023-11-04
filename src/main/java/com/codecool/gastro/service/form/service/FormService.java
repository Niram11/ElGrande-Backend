package com.codecool.gastro.service.form.service;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.entity.*;
import com.codecool.gastro.service.form.handler.FormHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {
    private final FormHandler<Restaurant> restaurantHandler;
    private final FormHandler<Address> addressHandler;
    private final FormHandler<BusinessHour> businessHourHandler;
    private final FormHandler<Location> locationHandler;
    private final FormHandler<Customer> customerHandler;

    public FormService(FormHandler<Restaurant> restaurantHandler,
                       FormHandler<Address> addressHandler,
                       FormHandler<BusinessHour> businessHourHandler,
                       FormHandler<Location> locationHandler,
                       FormHandler<Customer> customerHandler) {
        this.restaurantHandler = restaurantHandler;
        this.addressHandler = addressHandler;
        this.businessHourHandler = businessHourHandler;
        this.locationHandler = locationHandler;
        this.customerHandler = customerHandler;
    }

    @Transactional
    public void submitRestaurantForm(NewRestaurantFormDto newFormDto) {
        Restaurant restaurant = new Restaurant();
        handleRestaurantForm(newFormDto, restaurant);
    }

    private void handleRestaurantForm(NewRestaurantFormDto newFormDto, Restaurant restaurant) {
        restaurantHandler.handleRestaurantForm(newFormDto, restaurant);
        addressHandler.handleRestaurantForm(newFormDto, restaurant);
        locationHandler.handleRestaurantForm(newFormDto, restaurant);
        businessHourHandler.handleRestaurantForm(newFormDto, restaurant);
        customerHandler.handleRestaurantForm(newFormDto, restaurant);

    }

}
