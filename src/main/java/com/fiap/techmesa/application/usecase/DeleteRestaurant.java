package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.RestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteRestaurant {

	private final RestaurantGateway gateway;
	
	public void execute(final Integer id) {
		final var restaurant =
			gateway
				.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
		
		gateway.delete(restaurant.getId());
	}
}
