package com.fiap.techmesa.application.gateway;

import java.util.Optional;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;

public interface RestaurantGateway {
	
	Restaurant save(final Restaurant restaurant);
	
	Pagination<Restaurant> findAll(Page page);
	
	Optional<Restaurant> findById(final int id);
	
	Optional<Restaurant> findByTypeKitchen(final TypeKitchenEnum typeKitchen);
	
	Optional<Restaurant> findByPartName(final String partName);
	
	Optional<Restaurant> findByName(final String name);
	
	Optional<Restaurant> findByEmail(final String email);
	
	Optional<Restaurant> findByCity(final String city);
	
	Optional<Restaurant> findByCityAndNeighborhood(final String city, final String neighborhood);
	
	void delete(final int id);

	Restaurant update(final Restaurant restaurant);
	

}
