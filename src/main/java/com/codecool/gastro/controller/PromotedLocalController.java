package com.codecool.gastro.controller;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.service.PromotedLocalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/promoted-locals")
public class PromotedLocalController {
    private final PromotedLocalService promotedLocalService;

    public PromotedLocalController(PromotedLocalService promotedLocalService) {
        this.promotedLocalService = promotedLocalService;
    }

    @GetMapping
    public ResponseEntity<List<PromotedLocalDto>> getAllPromotedLocals() {
        return ResponseEntity.status(HttpStatus.OK).body(promotedLocalService.getPromotedLocals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotedLocalDto> getPromotedLocal(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(promotedLocalService.getPromotedLocalBy(id));
    }

    @PostMapping
    public ResponseEntity<PromotedLocalDto> createNewPromotedLocal(@Valid @RequestBody NewPromotedLocalDto newPromotedLocalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotedLocalService.saveNewPromotedLocal(newPromotedLocalDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotedLocalDto> updatePromotedLocal(@PathVariable UUID id,
                                                @Valid @RequestBody NewPromotedLocalDto newPromotedLocalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotedLocalService.updatePromotedLocal(id, newPromotedLocalDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PromotedLocalDto> deletePromotedLocal(@PathVariable UUID id) {
        promotedLocalService.deletePromotedLocal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
