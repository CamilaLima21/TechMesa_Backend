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

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.dto.UpdateAddressRequest;
import com.fiap.techmesa.application.usecase.CreateAddress;
import com.fiap.techmesa.application.usecase.DeleteAddress;
import com.fiap.techmesa.application.usecase.GetAddress;
import com.fiap.techmesa.application.usecase.UpdateAddress;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/techmesa/address")
public class AddressController {
	
	private final CreateAddress createAddress;
	private final GetAddress getAddress;
	private final UpdateAddress updateAddress;
	private final DeleteAddress deleteAddress;
	
	@PostMapping
	public ResponseEntity<Address> createAddress(final @RequestBody @Valid Address address) {
		 final var createdAddress = createAddress.execute(address);
		 
		 URI location = 
			 ServletUriComponentsBuilder.fromCurrentRequest()
			 	.path("/{id}")
			 	.buildAndExpand(createdAddress.getId())
			 	.toUri();
		 
		 return ResponseEntity.created(location).body(createdAddress);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> findById(final @PathVariable int id) {
		return ResponseEntity.ok(getAddress.execute(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> update(
			final @PathVariable int id, 
			final @RequestBody @Valid UpdateAddressRequest updateAddressRequest) {
		return ResponseEntity.ok(updateAddress.execute(id, updateAddressRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(final @PathVariable int id) {
		deleteAddress.execute(id);
		
		return ResponseEntity.noContent().build();
	}

}
