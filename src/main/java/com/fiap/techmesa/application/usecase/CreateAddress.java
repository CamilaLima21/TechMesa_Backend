package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateAddress {
	
	private final AddressGateway gateway;
	
	public Address execute(final Address request) {
		
		final var buildDomain = 
				Address.createAddress(
						request.getStreet(),
						request.getNumber(),
						request.getNeighborhood(),
						request.getCity(),
						request.getState(),
						request.getCountry(),
						request.getCep());
		
		return gateway.save(buildDomain);
		
	}

}
