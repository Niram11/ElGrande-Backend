package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.service.OwnershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ownerships")
public class OwnershipController {
    private final OwnershipService ownershipService;

    public OwnershipController(OwnershipService ownershipService) {
        this.ownershipService = ownershipService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OwnershipDto> getOwnershipById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ownershipService.getOwnershipById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OwnershipDto> createOwnership(@Valid @RequestBody NewOwnershipDto newOwnershipDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownershipService.saveOwnership(newOwnershipDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnershipDto> updateOwnership(@PathVariable UUID id, @Valid @RequestBody NewOwnershipDto newOwnershipDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownershipService.updateOwnership(id, newOwnershipDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnershipDto> deleteOwnership(@PathVariable UUID id) {
        ownershipService.deleteOwnership(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}