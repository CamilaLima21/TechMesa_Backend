package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.dto.UpdateAddressRequest;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.usecase.exception.AddressNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateAddress {
	
	private final AddressGateway gateway;
	
	public Address execute(
			final int id, final UpdateAddressRequest updateAddressRequest) {
		final var addressFound = 
			gateway
				.findById(id)
				.orElseThrow(() -> new AddressNotFoundException(id));
	 
		addressFound.setStreet(updateAddressRequest.getStreet());
		addressFound.setNumber(updateAddressRequest.getNumber());
		addressFound.setNeighborhood(updateAddressRequest.getNeighborhood());
		addressFound.setCity(updateAddressRequest.getCity());
		addressFound.setState(updateAddressRequest.getState());
		addressFound.setCountry(updateAddressRequest.getCountry());
		addressFound.setCep(updateAddressRequest.getCep());
		
		return gateway.update(addressFound);
	}
}
