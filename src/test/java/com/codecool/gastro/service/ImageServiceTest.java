package com.codecool.gastro.service;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.ImageRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Image;
import com.codecool.gastro.repository.entity.Restaurant;
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
    ImageService service;
    @Mock
    ImageRepository repository;
    @Mock
    ImageMapper mapper;
    @Mock
    RestaurantRepository restaurantRepository;

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
    void testGetImagesByRestaurant_ShouldReturnList_WhenCalled() {
        // when
        List<ImageDto> list = service.getImagesByRestaurantId(restaurantId);

        // then
        assertEquals(list.size(), 0);
    }

    @Test
    void testSaveNewImage_ShouldReturnImageDto_WhenCalled() {
        // when
        when(mapper.dtoToImage(newImageDto)).thenReturn(image);
        when(repository.save(image)).thenReturn(image);
        when(mapper.toDto(image)).thenReturn(imageDto);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));
        ImageDto testedImageDto = service.saveNewImage(newImageDto);

        // then
        assertEquals(newImageDto.pathToImage(), testedImageDto.pathToImage());
    }

}