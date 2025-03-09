package com.fiap.techmesa.infrastructure.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingGateway ratingGateway;

    @PostMapping
    public ResponseEntity<Rating> createRating(@Validated @RequestBody Rating rating) {
        Rating savedRating = ratingGateway.save(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable int id) {
        Optional<Rating> rating = ratingGateway.findById(id);
        return rating.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable int id, @Validated @RequestBody Rating rating) {
        rating.setId(id);
        Rating updatedRating = ratingGateway.update(rating);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable int id) {
        ratingGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingGateway.findAll();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
