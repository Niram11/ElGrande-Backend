package com.codecool.gastro.controller;

import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.image.NewImageDto;
import com.codecool.gastro.service.ImageService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<ImageDto> getAllImage() {
        return imageService.getImages();
    }

    @GetMapping("/{id}")
    public ImageDto getImage(@PathVariable UUID id) {
        return imageService.getImageBy(id);
    }

    @PostMapping
    public ImageDto createNewImage(@Valid @RequestBody NewImageDto newImageDto) {
        return imageService.saveNewImage(newImageDto);
    }

    @PutMapping("/{id}")
    public ImageDto updateImage(@PathVariable UUID id, @Valid @RequestBody NewImageDto newImageDto) {
        return imageService.updateImage(id , newImageDto);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
    }
}
