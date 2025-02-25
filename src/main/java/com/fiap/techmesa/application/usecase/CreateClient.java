package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.exception.ClientAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateClient {
	
	private final ClientGateway gateway;
	
	public Client execute(final Client request) {
		
		final var client = gateway.findById(request.getId());
		
		if(client.isPresent()) {
			throw new ClientAlreadyExistsException(request.getId(), request.getName());
		}
		
		final var buildDomain =
				Client.createClient(
						request.getName(),
						request.getEmail(),
						request.getRegistrationDate(),
						request.getAddress(),
						request.getReserves());
		
		return gateway.save(buildDomain);
		
	}

}
