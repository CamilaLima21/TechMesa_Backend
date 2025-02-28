package com.fiap.techmesa.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;

public interface TableRestaurantRepository extends JpaRepository<TableRestaurantEntity, Integer>{

	TableRestaurantEntity findByTableIdentification(String tableIdentification);
	
	Optional<List<TableRestaurantEntity>> findByRestaurantAndReserveDateReserve(RestaurantEntity restaurantEntity, LocalDate dateReserve);
}