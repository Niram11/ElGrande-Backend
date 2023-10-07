package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.service.OwnershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ownerships")
public class OwnershipController {
    private final OwnershipService ownershipService;

    public OwnershipController(OwnershipService ownershipService) {
        this.ownershipService = ownershipService;
    }

    @GetMapping
    public ResponseEntity<List<OwnershipDto>> getAllOwnerships() {
        return ResponseEntity.status(HttpStatus.OK).body(ownershipService.getAllOwnerships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnershipDto> getOwnership(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ownershipService.getOwnership(id));
    }

    @PostMapping
    public ResponseEntity<OwnershipDto> createOwnership(@Valid @RequestBody NewOwnershipDto newOwnershipDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownershipService.saveOwnership(newOwnershipDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnershipDto> updateOwnership(@PathVariable UUID id, @Valid @RequestBody NewOwnershipDto newOwnershipDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownershipService.updateOwnership(id, newOwnershipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OwnershipDto> deleteOwnership(@PathVariable UUID id) {
        ownershipService.deleteOwnership(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}