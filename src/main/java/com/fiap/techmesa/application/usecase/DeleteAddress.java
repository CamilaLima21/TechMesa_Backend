package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.usecase.exception.AddressNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteAddress {
	
	private final AddressGateway gateway;
	
	public void execute(final Integer id) {
		final var address = 
			gateway
				.findById(id)
				.orElseThrow(() -> new AddressNotFoundException(id));
		
		gateway.delete(address.getId());			
		
	}

}
