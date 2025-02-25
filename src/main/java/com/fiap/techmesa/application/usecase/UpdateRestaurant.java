package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.dto.UpdateRestaurantRequest;
import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.RestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateRestaurant {
	
	private final RestaurantGateway gateway;
	
	public Restaurant execute(
			final int id, final UpdateRestaurantRequest updateRestaurantRequest) {
		final var restaurantFound =
				gateway
					.findById(id)
					.orElseThrow(() -> new RestaurantNotFoundException(id));
		
		restaurantFound.setName(updateRestaurantRequest.getName());
		restaurantFound.setEmail(updateRestaurantRequest.getEmail());
		restaurantFound.setOpeningHours(updateRestaurantRequest.getOpeningHours());
		restaurantFound.setTypeKitchen(updateRestaurantRequest.getTypeKitchen());
		restaurantFound.setCapacity(updateRestaurantRequest.getCapacity());
		
		return gateway.update(restaurantFound);
	}

}
