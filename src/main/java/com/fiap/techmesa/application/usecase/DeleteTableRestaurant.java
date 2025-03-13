package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.TableRestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteTableRestaurant {
	
	private final TableRestaurantGateway gateway;
	
	public void execute(final int id) {
		final var tableRestaurant =
			gateway
				.findById(id)
				.orElseThrow(() -> new TableRestaurantNotFoundException(id));
		
		gateway.delete(tableRestaurant.getTableIdentification());
	}

}
