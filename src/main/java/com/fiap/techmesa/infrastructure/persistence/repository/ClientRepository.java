package com.fiap.techmesa.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

	Optional<ClientEntity> findByEmail(String email);
	
	Optional<ClientEntity> findByName(String name);
	
	Optional<List<ClientEntity>> findByNameContainsIgnoreCase(String partName);

}
