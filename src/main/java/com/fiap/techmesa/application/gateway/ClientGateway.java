package com.fiap.techmesa.application.gateway;

import java.util.Optional;

import com.fiap.techmesa.application.domain.Client;

public interface ClientGateway {
	
	Client save(final Client client);
	
	Optional<Client> findByPartName(final String partName);
	
	Optional<Client> findByName(final String name);
	
	Optional<Client> findById(final int id);
	
	Optional<Client> findByEmail(final String email);
	
	Client update(final Client client);
	
	void delete(final int id);

}
