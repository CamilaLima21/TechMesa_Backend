package com.fiap.techmesa.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Integer>{

	Optional<List<ReserveEntity>> findByRestaurantAndDateReserve(RestaurantEntity restaurantEntity, LocalDate dateReserve);
	
	Optional<List<ReserveEntity>> findByClientAndDateReserve(ClientEntity clientEntity, LocalDate dateReserve);
	
	Optional<List<ReserveEntity>> findByRestaurantAndClientAndDateReserve(RestaurantEntity restaurantEntity, ClientEntity clientEntity, LocalDate dateReserve);
}
