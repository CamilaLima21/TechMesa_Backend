package com.fiap.techmesa.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer>{
	
	Optional<RestaurantEntity> findById(Integer id);

	RestaurantEntity findByEmail(String email);
	
	RestaurantEntity findByName(String name);
	
	Optional<List<RestaurantEntity>> findByNameContainsIgnoreCase(String partName);
	
	@Query("SELECT r FROM RestaurantEntity r WHERE r.address.city = :city")
	Optional<List<RestaurantEntity>> findByCity(String city);

	@Query("SELECT r FROM RestaurantEntity r WHERE r.address.city = :city AND r.address.neighborhood = :neighborhood")
	Optional<List<RestaurantEntity>> findByCityAndNeighborhood(String city, String neighborhood);

	
	Optional<List<RestaurantEntity>> findByTypeKitchen(TypeKitchenEnum typekitchen);
}