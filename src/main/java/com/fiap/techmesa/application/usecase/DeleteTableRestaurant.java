package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.TableRestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteTableRestaurant {
	
	private final TableRestaurantGateway gateway;
	
	public void execute(final String tableIdentification) {
		final var tableRestaurant =
			gateway
				.findById(tableIdentification)
				.orElseThrow(() -> new TableRestaurantNotFoundException(tableIdentification));
		
		gateway.delete(tableRestaurant.getTableIdentification());
	}

}
