package com.codecool.gastro.controller;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(params = {"restaurantId"})
    public ResponseEntity<List<ImageDto>> getImagesByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImagesByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<ImageDto> createNewImage(@Valid @RequestBody NewImageDto newImageDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.saveNewImage(newImageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImageDto> deleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
