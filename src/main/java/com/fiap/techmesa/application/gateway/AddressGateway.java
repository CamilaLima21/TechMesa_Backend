package com.fiap.techmesa.application.gateway;

import java.util.Optional;

import com.fiap.techmesa.application.domain.Address;

public interface AddressGateway {

	Address save(final Address address);
	
	Optional<Address> findById(final int id);
	
	Address update(final Address address);
	
	void delete(final int id);
}
