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

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.dto.UpdateRatingRequest;
import com.fiap.techmesa.application.usecase.CreateRating;
import com.fiap.techmesa.application.usecase.DeleteRating;
import com.fiap.techmesa.application.usecase.GetRating;
import com.fiap.techmesa.application.usecase.UpdateRating;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techmesa/ratings")
public class RatingController {

	private final CreateRating createRating;
	private final DeleteRating deleteRating;
	private final GetRating getRating;
	private final UpdateRating updateRating;
	
	@PostMapping
	public ResponseEntity<Rating> create(
			@RequestBody @Valid Rating rating, final @RequestParam int id) {
		final var createdRating = createRating.execute(rating, id);
		
		URI location =
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdRating.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(createdRating);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rating> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getRating.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Rating> update(
			final @PathVariable int id,
			final @RequestBody @Valid UpdateRatingRequest updateRatingRequest) {
		return ResponseEntity.ok(updateRating.execute(id, updateRatingRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final @PathVariable int id) {
		deleteRating.execute(id);
		return ResponseEntity.noContent().build();
	}
}
