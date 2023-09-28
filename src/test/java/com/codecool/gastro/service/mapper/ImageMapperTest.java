package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.entity.Image;
import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ImageMapperTest {

    private ImageMapper mapper = Mappers.getMapper(ImageMapper.class);

    private UUID imageId;
    private UUID restaurantId;
    private Image image;
    private NewImageDto newImageDto;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        imageId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        image = new Image();
        image.setId(imageId);
        image.setPathToImage("/path/to/image");
        image.setRestaurant(restaurant);

        newImageDto = new NewImageDto(
                "/test/image",
                restaurantId
        );
    }

    @Test
    void testToDto_ShouldReturnImageDto_WhenCalled() {
        // when
        ImageDto testedImageDto = mapper.toDto(image);

        // then
        assertEquals(image.getId(), testedImageDto.id());
        assertEquals(image.getPathToImage(), testedImageDto.pathToImage());
    }

    @Test
    void testDtoToImage_ShouldReturnImage_WhenCalled() {
        // when
        Image imageOne = mapper.dtoToImage(imageId);
        Image imageTwo = mapper.dtoToImage(imageId, newImageDto);
        Image imageThree = mapper.dtoToImage(newImageDto);

        // then
        assertEquals(imageId, imageOne.getId());

        assertEquals(imageId, imageTwo.getId());
        assertEquals(newImageDto.pathToImage(), imageTwo.getPathToImage());
        assertEquals(newImageDto.restaurantId(), imageTwo.getRestaurant().getId());

        assertEquals(newImageDto.pathToImage(), imageThree.getPathToImage());
        assertEquals(newImageDto.restaurantId(), imageThree.getRestaurant().getId());

    }

}