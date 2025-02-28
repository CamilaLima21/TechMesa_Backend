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

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.dto.UpdateTableRestaurantRequest;
import com.fiap.techmesa.application.usecase.CreateTableRestaurant;
import com.fiap.techmesa.application.usecase.DeleteTableRestaurant;
import com.fiap.techmesa.application.usecase.GetTableRestaurant;
import com.fiap.techmesa.application.usecase.UpdateTableRestaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techmesa/tableRestaurants")
public class TableRestaurantController {

	private final CreateTableRestaurant createTableRestaurant;
	private final DeleteTableRestaurant deleteTableRestaurant;
	private final GetTableRestaurant getTableRestaurant;
	private final UpdateTableRestaurant updateTableRestaurant;
	
	@PostMapping
	public ResponseEntity<TableRestaurant> create(
			@RequestBody @Valid TableRestaurant tableRestaurant) {
		final var createdTableRestaurant = createTableRestaurant.execute(tableRestaurant);
		
		URI location =
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdTableRestaurant.getTableIdentification())
				.toUri();
		
		return ResponseEntity.created(location).body(createdTableRestaurant);
	}
	
	@GetMapping("/{tableIdentification}")
	public ResponseEntity<TableRestaurant> findById(final @PathVariable String tableIdentification) {
		return ResponseEntity.ok(getTableRestaurant.execute(tableIdentification));
	}
	
	@PutMapping("/{tableIdentification}")
	public ResponseEntity<TableRestaurant> update(
			final @PathVariable String tableIdentification,
			final @RequestBody @Valid UpdateTableRestaurantRequest updateTableRestaurantRequest) {
		return ResponseEntity.ok(updateTableRestaurant.execute(tableIdentification, updateTableRestaurantRequest));
	}
	
	@DeleteMapping("/{tableIdentification}")
	public ResponseEntity<Void> delete(final @PathVariable String tableIdentification) {
		deleteTableRestaurant.execute(tableIdentification);
		return ResponseEntity.noContent().build();
	}
}
