package com.codecool.gastro.service;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.ImageRepository;
import com.codecool.gastro.repository.entity.Image;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public List<ImageDto> getImagesByRestaurantId(UUID id) {
        return imageRepository.findAllByRestaurant(id)
                .stream()
                .map(imageMapper::toDto)
                .toList();
    }

    public ImageDto saveNewImage(NewImageDto newImageDto) {
        Image savedImage = imageRepository.save(imageMapper.dtoToImage(newImageDto));
        return imageMapper.toDto(savedImage);
    }

    public void deleteImage(UUID id) {
        imageRepository.delete(imageMapper.dtoToImage(id));
    }

}
