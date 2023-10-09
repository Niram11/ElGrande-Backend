package com.codecool.gastro.controller;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(params = {"restaurantId"})
    public ResponseEntity<AddressDto> getAddressByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddressByRestaurantId(restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @Valid @RequestBody NewAddressDto newAddressDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.updateAddress(id, newAddressDto));
    }

}
