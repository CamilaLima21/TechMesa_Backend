package com.fiap.techmesa.infrastructure.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.gateway.ClientGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientGateway clientGateway;

    @PostMapping
    public ResponseEntity<Client> createClient(@Validated @RequestBody Client client) {
        Client savedClient = clientGateway.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Optional<Client> client = clientGateway.findById(id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @Validated @RequestBody Client client) {
        client.setId(id);
        Client updatedClient = clientGateway.update(client);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        clientGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-part-name")
    public ResponseEntity<Client> getClientByPartName(@RequestParam String partName) {
        Optional<Client> client = clientGateway.findByPartName(partName);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-name")
    public ResponseEntity<Client> getClientByName(@RequestParam String name) {
        Optional<Client> client = clientGateway.findByName(name);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-email")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        Optional<Client> client = clientGateway.findByEmail(email);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
