package com.fiap.techmesa.infrastructure.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/table-restaurants")
@RequiredArgsConstructor
public class TableRestaurantController {

    private final TableRestaurantGateway tableRestaurantGateway;

    @PostMapping
    public ResponseEntity<TableRestaurant> createTableRestaurant(@Validated @RequestBody TableRestaurant tableRestaurant) {
        TableRestaurant savedTableRestaurant = tableRestaurantGateway.save(tableRestaurant);
        return new ResponseEntity<>(savedTableRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableRestaurant> getTableRestaurantById(@PathVariable String tableIdentification) {
        Optional<TableRestaurant> tableRestaurant = tableRestaurantGateway.findById(tableIdentification);
        return tableRestaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableRestaurant> updateTableRestaurant(@PathVariable int id, @Validated @RequestBody TableRestaurant tableRestaurant) {
        tableRestaurant.setId(id);
        TableRestaurant updatedTableRestaurant = tableRestaurantGateway.update(tableRestaurant);
        return new ResponseEntity<>(updatedTableRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableRestaurant(@PathVariable String tableIdentification) {
        tableRestaurantGateway.delete(tableIdentification);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Pagination<TableRestaurant>> getAllTableRestaurants(@RequestParam int page, @RequestParam int size) {
        Page pageRequest = new Page(page, size);
        Pagination<TableRestaurant> tableRestaurants = tableRestaurantGateway.findAll(pageRequest);
        return new ResponseEntity<>(tableRestaurants, HttpStatus.OK);
    }

    @GetMapping("/by-restaurant-and-date")
    public ResponseEntity<List<TableRestaurant>> getTableRestaurantsByRestaurantAndDate(@RequestParam String tableIdentification, @RequestParam Integer reserveId) {
        Optional<TableRestaurant> tableRestaurant = tableRestaurantGateway.findByRestaurantAndDate(tableIdentification, reserveId);
        return tableRestaurant.map(value -> new ResponseEntity<>(List.of(value), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-restaurant-not-reserved-and-date")
    public ResponseEntity<List<TableRestaurant>> getTableRestaurantsByRestaurantNotReservedAndDate(@RequestParam int id, @RequestParam StatusTableOccupationEnum statusTableOccupation, @RequestParam LocalDate dateReserve) {
        Optional<TableRestaurant> tableRestaurant = tableRestaurantGateway.findByRestaurantNotReservedAndDate(id, statusTableOccupation, dateReserve);
        return tableRestaurant.map(value -> new ResponseEntity<>(List.of(value), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
