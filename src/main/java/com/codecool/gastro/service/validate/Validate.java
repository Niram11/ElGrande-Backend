package com.codecool.gastro.service.validate;


import com.codecool.gastro.dto.NewEntityDto;

public interface Validate <T extends NewEntityDto>{

    void validateUpdate(T dto);
}