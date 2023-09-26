package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository repository;

    private UUID imageId;
    private UUID restaurantId;


    @BeforeEach
    void setUp() {
        imageId = UUID.fromString("d9ea6cf3-b18d-4a6c-a023-0747345bd275");
        restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");
    }

    @Test
    void testFindAll_ShouldReturnListOfImages_WhenCalled() {
        // when
        List<Image> list = repository.findALl();

        // then
        assertEquals(list.size(), 4);
    }

    @Test
    void testFindById_ShouldReturnImage_WhenExist() {
        // when
        Optional<Image> image = repository.findById(imageId);

        // then
        assertTrue(image.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoImage() {
        // when
        Optional<Image> image = repository.findById(UUID.randomUUID());

        // then
        assertTrue(image.isEmpty());
    }

    @Test
    void testFindAllByRestaurant_ShouldReturnListOfImagesThatBelongsToRestaurant_WhenCalled() {
        // when
        List<Image> list = repository.findAllByRestaurant(restaurantId);

        // then
        assertEquals(list.size(), 2);
    }
}