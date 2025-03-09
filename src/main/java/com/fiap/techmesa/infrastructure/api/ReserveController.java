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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.dto.UpdateReserveRequest;
import com.fiap.techmesa.application.usecase.CreateReserve;
import com.fiap.techmesa.application.usecase.DeleteReserve;
import com.fiap.techmesa.application.usecase.GetReserve;
import com.fiap.techmesa.application.usecase.UpdateReserve;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("techMesa/reserves")
public class ReserveController {
	
	private final CreateReserve createReserve;
	private final DeleteReserve deleteReserve;
	private final GetReserve getReserve;
	private final UpdateReserve updateReserve;
	
	@PostMapping
	public ResponseEntity<Reserve> create(
			@RequestBody @Valid Reserve reserve, final @RequestParam int id)  {
		final var createdReserve = createReserve.execute(reserve, id);
		
		URI location = 
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdReserve.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(createdReserve);
	} 
		
	@GetMapping("/{id}")
	public ResponseEntity<Reserve> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getReserve.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Reserve> update(
			final @PathVariable int id,
			final @RequestBody @Valid UpdateReserveRequest updateReserveRequest) {
		return ResponseEntity.ok(updateReserve.execute(id, updateReserveRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final @PathVariable int id) {
		deleteReserve.execute(id);
		return ResponseEntity.noContent().build();
	}

}
