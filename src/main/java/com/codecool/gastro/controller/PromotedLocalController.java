package com.codecool.gastro.controller;

import com.codecool.gastro.dto.promotedlocal.EditPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.service.PromotedLocalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PromotedLocalDto> createNewPromotedLocal(@Valid @RequestBody NewPromotedLocalDto newPromotedLocalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotedLocalService.saveNewPromotedLocal(newPromotedLocalDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PromotedLocalDto> updatePromotedLocal(@PathVariable UUID id,
                                                                @Valid @RequestBody EditPromotedLocalDto editPromotedLocalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotedLocalService.updatePromotedLocal(id, editPromotedLocalDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PromotedLocalDto> deletePromotedLocal(@PathVariable UUID id) {
        promotedLocalService.deletePromotedLocal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
