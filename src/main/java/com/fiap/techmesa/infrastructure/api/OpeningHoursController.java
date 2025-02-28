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

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.dto.UpdateOpeningHoursRequest;
import com.fiap.techmesa.application.usecase.CreateOpeningHours;
import com.fiap.techmesa.application.usecase.DeleteOpeningHours;
import com.fiap.techmesa.application.usecase.GetOpeningHours;
import com.fiap.techmesa.application.usecase.UpdateOpeningHours;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/techmesa/openingHours")
public class OpeningHoursController {
	
	private final CreateOpeningHours createOpeningHours;
	private final DeleteOpeningHours deleteOpeningHours;
	private final GetOpeningHours getOpeningHours;
	private final UpdateOpeningHours updateOpeningHours;
	
	@PostMapping
	public ResponseEntity<OpeningHours> create(
			@RequestBody @Valid OpeningHours openingHours) {
		final var createdOpeningHours = createOpeningHours.execute(openingHours);
		
		URI location =
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdOpeningHours.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(createdOpeningHours);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OpeningHours> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getOpeningHours.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OpeningHours> update(
			final @PathVariable int id,
			final @RequestBody @Valid UpdateOpeningHoursRequest openingHoursRequest) {
		return ResponseEntity.ok(updateOpeningHours.execute(id, openingHoursRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final @PathVariable int id) {
		deleteOpeningHours.execute(id);
		return ResponseEntity.noContent().build();
	}
}
