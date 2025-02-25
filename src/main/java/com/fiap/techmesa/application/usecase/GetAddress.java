package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.usecase.exception.AddressNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAddress {
	
	private final AddressGateway gateway;
	
	public Address execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new AddressNotFoundException(id));
	}

}
