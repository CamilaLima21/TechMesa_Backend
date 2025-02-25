package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.dto.UpdateClientRequest;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.exception.ClientNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateClient {
	
	private final ClientGateway gateway;
	
	public Client execute (final int id, final UpdateClientRequest updateClientRequest) {
		final var clientFound =
			gateway
				.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
		
		clientFound.setName(updateClientRequest.getName());
		clientFound.setEmail(updateClientRequest.getEmail());
		clientFound.setAddress(updateClientRequest.getAddress());
		clientFound.setReserves(updateClientRequest.getReserves());
		
		return gateway.update(clientFound);
	}

}
