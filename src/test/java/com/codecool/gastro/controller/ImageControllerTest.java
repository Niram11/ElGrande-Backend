package com.codecool.gastro.controller;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Image;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.ImageService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.checkerframework.checker.units.qual.UnknownUnits;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ImageService service;
    @MockBean
    private RestaurantRepository restaurantRepository;

    private UUID imageId;
    private UUID restaurantId;
    private ImageDto imageDto;

    private String contentResponse;

    @BeforeEach
    void setUp() {
        imageId = UUID.fromString("8b8e9417-335e-4c28-aac3-fb732b72bb28");
        restaurantId = UUID.fromString("3d34a701-212c-4183-990b-eb8541a7551a");

        imageDto = new ImageDto(
                imageId,
                "/path/to/image"
        );

        contentResponse = """
                {
                    "id": "8b8e9417-335e-4c28-aac3-fb732b72bb28",
                    "pathToImage": "/path/to/image"
                }
                """;
    }

    @Test
    void testGetImageById_ShouldReturnStatusOdAndImageDto_WhenExist() throws Exception {
        // when
        when(service.getImageById(imageId)).thenReturn(imageDto);

        // then
        mockMvc.perform(get("/api/v1/images/" + imageId))
                .andExpectAll(status().isOk(),
                        content().json(contentResponse)
                );

    }

    @Test
    void testGetImageById_ShouldReturnStatusNotFoundAndErrorMessage_WhenNoImage() throws Exception {
        // when
        when(service.getImageById(imageId)).thenThrow(new ObjectNotFoundException(imageId, Image.class));

        // then
        mockMvc.perform(get("/api/v1/images/" + imageId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage").value("Object of class Image and id " + imageId + " cannot be found")
                );
    }

    @Test
    void testGetImagesByRestaurant_ShouldReturnStatusOkAndList_WhenCalled() throws Exception {
        // then
        mockMvc.perform(get("/api/v1/images?restaurantId=" + restaurantId))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testCreateNewImage_ShouldReturnStatusCreatedAndImageDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "pathToImage": "/path/to/image",
                    "restaurantId": "3d34a701-212c-4183-990b-eb8541a7551a"
                }
                """;

        // when
        when(service.saveNewImage(any(NewImageDto.class))).thenReturn(imageDto);
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Restaurant()));

        // then
        mockMvc.perform(post("/api/v1/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testCreateNewImage_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "pathToImage": "",
                    "restaurantId": "3d34a701-212c-4183-990b-eb8541a7551a"
                }
                """;

        // when
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // then
        mockMvc.perform(post("/api/v1/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Path to image cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );
    }

    @Test
    void testUpdateImage_ShouldReturnStatusCreatedAndImageDto_WhenValidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "pathToImage": "/path/to/image",
                    "restaurantId": "3d34a701-212c-4183-990b-eb8541a7551a"
                }
                """;

        // when
        when(service.updateImage(any(UUID.class), any(NewImageDto.class))).thenReturn(imageDto);
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Restaurant()));

        // then
        mockMvc.perform(put("/api/v1/images/" + imageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentResponse)
                );
    }

    @Test
    void testUpdateImage_ShouldReturnStatusBadRequestAndErrorMessages_WhenInvalidValues() throws Exception {
        // given
        String contentRequest = """
                {
                    "pathToImage": "",
                    "restaurantId": "3d34a701-212c-4183-990b-eb8541a7551a"
                }
                """;

        // when
        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // then
        mockMvc.perform(put("/api/v1/images/" + imageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.errorMessage", Matchers.containsString("Path to image cannot be empty")),
                        jsonPath("$.errorMessage", Matchers.containsString("Restaurant with this id does not exist"))
                );
    }

}