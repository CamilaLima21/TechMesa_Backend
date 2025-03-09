package com.fiap.techmesa.infrastructure.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("techMesa/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressGateway addressGateway;

    @PostMapping
    public ResponseEntity<Address> createAddress(@Validated @RequestBody Address address) {
        Address savedAddress = addressGateway.save(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable int id) {
        Optional<Address> address = addressGateway.findById(id);
        return address.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable int id, @Validated @RequestBody Address address) {
        address.setId(id);
        Address updatedAddress = addressGateway.update(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable int id) {
        addressGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
