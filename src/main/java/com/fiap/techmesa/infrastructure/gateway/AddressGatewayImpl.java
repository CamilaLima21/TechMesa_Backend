package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.usecase.exception.AddressNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {
	
	private final AddressRepository addressRepository;

	@Override
	public Address save(final Address request) {	
		final var entity =
			AddressEntity.builder()
				.street(request.getStreet())
				.number(request.getNumber())
				.neighborhood(request.getNeighborhood())
				.city(request.getCity())
				.state(request.getState())
				.country(request.getCountry())
				.cep(request.getCep())
				.build();
		
		final var saved = addressRepository.save(entity);
		
		return this.toResponse(saved);
	}

	@Override
	public Optional<Address> findById(final int id) {
		return addressRepository.findById(id).map(this::toResponse);
	}

	@Override
	public Address update(final Address request) {
		final var addressFound =
			addressRepository
				.findById(request.getId())
				.orElseThrow(() -> new AddressNotFoundException(request.getId()));
		
		final var newAddress =
			AddressEntity.builder()
				.id(addressFound.getId())
				.street(addressFound.getStreet())
				.number(addressFound.getNumber())
				.neighborhood(addressFound.getNeighborhood())
				.city(addressFound.getCity())
				.state(addressFound.getState())
				.country(addressFound.getCountry())
				.cep(addressFound.getCep())
				.build();
		
		final var updated = addressRepository.save(newAddress);
		
		return this.toResponse(updated);
	}

	@Transactional
	@Override
	public void delete(int id) {
		addressRepository.deleteById(id);
	}
	
	private Address toResponse(final AddressEntity entity) {
		return new Address(
				entity.getId(),
				entity.getStreet(),
				entity.getNumber(),
				entity.getNeighborhood(),
				entity.getCity(),
				entity.getState(),
				entity.getCountry(),
				entity.getCep());
	}

}
