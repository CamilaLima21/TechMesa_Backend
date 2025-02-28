package com.fiap.techmesa.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

	Optional<AddressEntity> findById(Integer id);
	
	void deleteById(Integer id);

}
