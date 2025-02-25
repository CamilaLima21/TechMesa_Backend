package com.fiap.techmesa.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.application.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
