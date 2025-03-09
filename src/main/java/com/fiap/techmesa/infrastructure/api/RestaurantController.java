package com.fiap.techmesa.infrastructure.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.application.gateway.RestaurantGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantGateway restaurantGateway;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Validated @RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantGateway.save(restaurant);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id) {
        Optional<Restaurant> restaurant = restaurantGateway.findById(id);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id, @Validated @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        Restaurant updatedRestaurant = restaurantGateway.update(restaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable int id) {
        restaurantGateway.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Pagination<Restaurant>> getAllRestaurants(@RequestParam int page, @RequestParam int size) {
        Page pageRequest = new Page(page, size);
        Pagination<Restaurant> restaurants = restaurantGateway.findAll(pageRequest);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/by-type-kitchen")
    public ResponseEntity<Restaurant> getRestaurantByTypeKitchen(@RequestParam String typeKitchen) {
        Optional<Restaurant> restaurant = restaurantGateway.findByTypeKitchen(TypeKitchenEnum.valueOf(typeKitchen));
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-part-name")
    public ResponseEntity<Restaurant> getRestaurantByPartName(@RequestParam String partName) {
        Optional<Restaurant> restaurant = restaurantGateway.findByPartName(partName);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-name")
    public ResponseEntity<Restaurant> getRestaurantByName(@RequestParam String name) {
        Optional<Restaurant> restaurant = restaurantGateway.findByName(name);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-email")
    public ResponseEntity<Restaurant> getRestaurantByEmail(@RequestParam String email) {
        Optional<Restaurant> restaurant = restaurantGateway.findByEmail(email);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-city")
    public ResponseEntity<Restaurant> getRestaurantByCity(@RequestParam String city) {
        Optional<Restaurant> restaurant = restaurantGateway.findByCity(city);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-city-and-neighborhood")
    public ResponseEntity<Restaurant> getRestaurantByCityAndNeighborhood(@RequestParam String city, @RequestParam String neighborhood) {
        Optional<Restaurant> restaurant = restaurantGateway.findByCityAndNeighborhood(city, neighborhood);
        return restaurant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
