package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant.id")
    Image dtoToImage(NewImageDto newImageDto);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "pathToImage", ignore = true)
    @Mapping(target = "id", source = "id")
    Image dtoToImage(UUID id);
}
