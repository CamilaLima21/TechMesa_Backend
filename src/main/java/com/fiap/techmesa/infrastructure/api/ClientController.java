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

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.dto.UpdateClientRequest;
import com.fiap.techmesa.application.usecase.CreateClient;
import com.fiap.techmesa.application.usecase.DeleteClient;
import com.fiap.techmesa.application.usecase.GetClient;
import com.fiap.techmesa.application.usecase.UpdateClient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techmesa/client")
public class ClientController {

	private final CreateClient createClient;
	private final DeleteClient deleteClient;
	private final GetClient getClient;
	private final UpdateClient updateClient;
	
	
	@PostMapping
	public ResponseEntity<Client> create(
			@RequestBody @Valid Client client) {
		final var createdClient = createClient.execute(client);
		
		URI location = 
			ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdClient.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(createdClient);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getClient.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(
			final @PathVariable int id,
			final @RequestBody @Valid UpdateClientRequest updateClientRequest) {
		return ResponseEntity.ok(updateClient.execute(id, updateClientRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final int id) {
		deleteClient.execute(id);
		return ResponseEntity.noContent().build();
	}
}
