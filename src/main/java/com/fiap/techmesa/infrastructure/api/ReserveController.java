package com.fiap.techmesa.infrastructure.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("techMesa/reserves")
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveGateway reserveGateway;

    @PostMapping
    public ResponseEntity<Reserve> createReserve(@Validated @RequestBody Reserve reserve) {
        Reserve savedReserve = reserveGateway.save(reserve);
        return new ResponseEntity<>(savedReserve, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(@PathVariable int id) {
        Optional<Reserve> reserve = reserveGateway.findById(id);
        return reserve.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserve> updateReserve(@PathVariable int id, @Validated @RequestBody Reserve reserve) {
        reserve.setId(id);
        Reserve updatedReserve = reserveGateway.update(reserve);
        return new ResponseEntity<>(updatedReserve, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable int id) {
        reserveGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Pagination<Reserve>> getAllReserves(@RequestParam int page, @RequestParam int size) {
        Page pageRequest = new Page(page, size);
        Pagination<Reserve> reserves = reserveGateway.findAll(pageRequest);
        return new ResponseEntity<>(reserves, HttpStatus.OK);
    }

    @GetMapping("/by-restaurant-date")
    public ResponseEntity<List<ReserveEntity>> getReservesByRestaurantAndDate(@RequestParam Integer restaurantId,
                                                                              @RequestParam LocalDate dateReserve) {
        Optional<List<ReserveEntity>> reserves = reserveGateway.findByRestaurantIdAndDate(restaurantId, dateReserve);
        return reserves.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
    