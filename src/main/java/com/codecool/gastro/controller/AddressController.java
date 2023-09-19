package com.codecool.gastro.controller;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(addressService.getAddressById(id));
    }

    @GetMapping(params = {"restaurantId"})
    public ResponseEntity<AddressDto> getAddressByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(addressService.getAddressByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<AddressDto> createNewAddress(@Valid @RequestBody NewAddressDto newAddressDto) {
        AddressDto addressDto = addressService.saveNewAddress(newAddressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @Valid @RequestBody NewAddressDto newAddressDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.updateAddress(id, newAddressDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
