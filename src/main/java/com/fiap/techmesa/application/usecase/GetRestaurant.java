package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.RestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetRestaurant {
	
	private final RestaurantGateway gateway;
	
	public Restaurant execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
	}

}
