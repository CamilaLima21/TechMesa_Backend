package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;

@Component
public class AddressGatewayImpl implements AddressGateway {

	@Override
	public Address save(Address address) {	
		//e99 implementar a persistencia do endereco
		return null;
	}

	@Override
	public Optional<Address> findById(int id) {
		//e99 implementar a persistencia do endereco
		return null;
	}

	@Override
	public Address update(Address address) {
		//e99 implementar a persistencia do endereco
		return null;
	}

	@Override
	public void delete(int id) {
		//e99 implementar a persistencia do endereco

	}

}
