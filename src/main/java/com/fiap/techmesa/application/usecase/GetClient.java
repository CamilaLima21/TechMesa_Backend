package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.exception.ClientNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetClient {
	
	private final ClientGateway gateway;
	
	public Client execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
	}

}
