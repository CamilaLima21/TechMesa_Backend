package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.RestaurantAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateRestaurant {

	private final RestaurantGateway gateway;
	
	public Restaurant execute(final Restaurant request) {
		
		final var restaurant = gateway.findById(request.getId());
		
		if(restaurant.isPresent()) {
			throw new RestaurantAlreadyExistsException(request.getId(), request.getName());
		}
		
		final var buildDomain =
				Restaurant.createRestaurant(
						request.getName(),
						request.getAddress(),
						request.getEmail(),
						request.getOpeningHours(),
						request.getReserves(),
						request.getTableRestaurants(),
						request.getTypeKitchen(),
						request.getCapacity(),
						request.getStatusRestaurant(),
						request.getRegistrationDate());
		
		return gateway.save(buildDomain);		
	}
}
