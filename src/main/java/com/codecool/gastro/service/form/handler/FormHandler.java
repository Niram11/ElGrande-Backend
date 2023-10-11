package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.entity.EntityObject;
import com.codecool.gastro.repository.entity.Restaurant;

public interface FormHandler<T extends EntityObject> {
    void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant);

}
