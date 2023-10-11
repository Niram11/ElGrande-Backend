package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.NewEntityDto;
import com.codecool.gastro.repository.entity.EntityObject;
import com.codecool.gastro.repository.entity.Restaurant;

public interface FormHandler<T extends EntityObject, U extends NewEntityDto> {
    T handleDto(U newEntityDto, Restaurant restaurant);
}
