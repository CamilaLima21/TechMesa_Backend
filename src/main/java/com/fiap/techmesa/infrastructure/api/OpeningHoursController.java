package com.fiap.techmesa.infrastructure.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/opening-hours")
@RequiredArgsConstructor
public class OpeningHoursController {

    private final OpeningHoursGateway openingHoursGateway;

    @PostMapping
    public ResponseEntity<OpeningHours> createOpeningHours(@Validated @RequestBody OpeningHours openingHours) {
        OpeningHours savedOpeningHours = openingHoursGateway.save(openingHours);
        return new ResponseEntity<>(savedOpeningHours, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpeningHours> getOpeningHoursById(@PathVariable int id) {
        Optional<OpeningHours> openingHours = openingHoursGateway.findById(id);
        return openingHours.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpeningHours> updateOpeningHours(@PathVariable int id, @Validated @RequestBody OpeningHours openingHours) {
        openingHours.setId(id);
        OpeningHours updatedOpeningHours = openingHoursGateway.update(openingHours);
        return new ResponseEntity<>(updatedOpeningHours, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpeningHours(@PathVariable int id) {
        openingHoursGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<OpeningHours>> getAllOpeningHours() {
        List<OpeningHours> openingHours = openingHoursGateway.findAll();
        return new ResponseEntity<>(openingHours, HttpStatus.OK);
    }
}
