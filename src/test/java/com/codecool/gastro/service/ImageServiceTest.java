package com.codecool.gastro.service;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.ImageRepository;
import com.codecool.gastro.repository.entity.Image;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ImageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @InjectMocks
    private ImageService service;
    @Mock
    private ImageRepository repository;
    @Mock
    private ImageMapper mapper;

    private UUID imageId;
    private UUID restaurantId;
    private Image image;
    private ImageDto imageDto;
    private NewImageDto newImageDto;

    @BeforeEach
    void setUp() {
        imageId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        image = new Image();

        imageDto = new ImageDto(
                imageId,
                "path/to/image"
        );

        newImageDto = new NewImageDto(
                "path/to/image",
                restaurantId
        );
    }

    @Test
    void testGetImages_ShouldReturnList_WhenCalled() {
        // when
        List<ImageDto> list = service.getImages();

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testGetImageById_ShouldReturnImageDto_WhenExist() {
        // when
        when(repository.findById(imageId)).thenReturn(Optional.of(image));
        when(mapper.toDto(image)).thenReturn(imageDto);
        ImageDto testedImageDto = service.getImageById(imageId);

        // then
        assertEquals(imageDto.id(), testedImageDto.id());
        assertEquals(imageDto.pathToImage(), testedImageDto.pathToImage());
    }

    @Test
    void testGetImageById_ShouldThrowObjectNotFoundException_WhenNoImage() {
        // then
        assertThrows(ObjectNotFoundException.class, () -> service.getImageById(imageId));
    }

    @Test
    void testGetImagesByRestaurant_ShouldReturnList_WhenCalled() {
        // when
        List<ImageDto> list = service.getImagesByRestaurant(restaurantId);

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testSaveNewImage_ShouldReturnImageDto_WhenCalled() {
        // when
        when(mapper.dtoToImage(newImageDto)).thenReturn(image);
        when(repository.save(image)).thenReturn(image);
        when(mapper.toDto(image)).thenReturn(imageDto);
        ImageDto testedImageDto = service.saveNewImage(newImageDto);

        // then
        assertEquals(newImageDto.pathToImage(), testedImageDto.pathToImage());
    }

    @Test
    void testUpdateImage_ShouldReturnImageDto_WhenCalled() {
        // when
        when(mapper.dtoToImage(imageId, newImageDto)).thenReturn(image);
        when(repository.save(image)).thenReturn(image);
        when(mapper.toDto(image)).thenReturn(imageDto);
        ImageDto testedImageDto = service.updateImage(imageId, newImageDto);

        // then
        assertEquals(imageId, testedImageDto.id());
        assertEquals(newImageDto.pathToImage(), testedImageDto.pathToImage());
    }

}