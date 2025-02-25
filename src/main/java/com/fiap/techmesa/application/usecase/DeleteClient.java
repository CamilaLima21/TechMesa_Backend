package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.exception.ClientNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteClient {
	
	private final ClientGateway gateway;
	
	public void execute(final Integer id) {
		final var client =
			gateway
				.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
		
		gateway.delete(client.getId());
	}

}
