package com.codecool.gastro.controller;

import com.codecool.gastro.dto.address.AddressDto;
import com.codecool.gastro.dto.address.NewAddressDto;
import com.codecool.gastro.service.AddressService;
import jakarta.validation.Valid;
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
    public List<AddressDto> getAllAddresses() {
        return addressService.getAddresses();
    }

    @GetMapping("/{id}")
    public AddressDto getAddress(@PathVariable UUID id) {
        return addressService.getAddressBy(id);
    }

    @GetMapping(params = {"restaurantId"})
    public AddressDto getAddressByRestaurantId(@RequestParam("restaurantId") UUID restaurantId) {
        return addressService.getAddressByRestaurantId(restaurantId);
    }

    @PostMapping
    public AddressDto createNewAddress(@Valid @RequestBody NewAddressDto newAddressDto) {
        return addressService.saveAddress(newAddressDto);
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(@PathVariable UUID id, @Valid @RequestBody NewAddressDto newAddressDto) {
        return addressService.updateAddress(id, newAddressDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }
}
