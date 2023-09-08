package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.entity.Image;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);

    Image dtoToImage(NewImageDto newImageDto);

    Image dtoToImage(NewImageDto newImageDto, UUID id);

    Image dtoToImage(UUID id);
}
