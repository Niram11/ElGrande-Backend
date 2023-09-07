package com.codecool.gastro.controller;

import com.codecool.gastro.dto.ownership.NewOwnershipDto;
import com.codecool.gastro.dto.ownership.OwnershipDto;
import com.codecool.gastro.service.OwnershipService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ownerships")
public class OwnershipController
{
    private final OwnershipService ownershipService;

    public OwnershipController(OwnershipService ownershipService)
    {
        this.ownershipService = ownershipService;
    }

    @GetMapping
    public List<OwnershipDto> getAllOwnerships()
    {
        return ownershipService.getAllOwnerships();
    }

    @GetMapping("/{id}")
    public OwnershipDto getOwnership(@PathVariable UUID id)
    {
        return ownershipService.getOwnership(id);
    }

    @PostMapping
    public OwnershipDto createOwnership(@Valid @RequestBody NewOwnershipDto newOwnershipDto)
    {
        return ownershipService.saveOwnership(newOwnershipDto);
    }

    @PutMapping("/{id}")
    public OwnershipDto updateOwnership(@PathVariable UUID id, @Valid @RequestBody NewOwnershipDto newOwnershipDto)
    {
        return ownershipService.updateOwnership(id, newOwnershipDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOwnership(@PathVariable UUID id)
    {
        ownershipService.deleteOwnership(id);
    }
}