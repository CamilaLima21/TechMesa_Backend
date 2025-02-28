package com.fiap.techmesa.infrastructure.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.dto.UpdateRestaurantRequest;
import com.fiap.techmesa.application.usecase.CreateRestaurant;
import com.fiap.techmesa.application.usecase.DeleteRestaurant;
import com.fiap.techmesa.application.usecase.GetRestaurant;
import com.fiap.techmesa.application.usecase.UpdateRestaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techmesa/restaurants")
public class RestaurantController {

	private final CreateRestaurant createRestaurant;
	private final GetRestaurant getRestaurant;
	private final DeleteRestaurant deleteRestaurant;
	private final UpdateRestaurant updateRestaurant;
	
	@PostMapping
	public ResponseEntity<Restaurant> create(
			@RequestBody @Valid Restaurant restaurant) {
		final var createdRestaurant = createRestaurant.execute(restaurant);
		
		URI location =
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdRestaurant.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(createdRestaurant);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getRestaurant.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> update(
			final @PathVariable int id,
			final @RequestBody @Valid UpdateRestaurantRequest updateRestaurantRequest) {
		return ResponseEntity.ok(updateRestaurant.execute(id, updateRestaurantRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final @PathVariable int id) {
		deleteRestaurant.execute(id);
		return ResponseEntity.noContent().build();
	}
}
