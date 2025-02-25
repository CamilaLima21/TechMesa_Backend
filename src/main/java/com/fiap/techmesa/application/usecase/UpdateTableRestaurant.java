package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.dto.UpdateTableRestaurantRequest;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.TableRestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateTableRestaurant {
	
	private final TableRestaurantGateway gateway;
	
	public TableRestaurant execute(
			final String tableIdentification, final UpdateTableRestaurantRequest updateTableRestaurantRequest) {
		final var tableRestaurantFound = 
			gateway
				.findById(tableIdentification)
				.orElseThrow(() -> new TableRestaurantNotFoundException(tableIdentification));
		
		return gateway.update(tableRestaurantFound);
		
	}

}
