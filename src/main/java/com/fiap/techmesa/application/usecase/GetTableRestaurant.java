package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.TableRestaurantNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetTableRestaurant {
	
	private final TableRestaurantGateway gateway;
	
	public TableRestaurant execute(final String tableIdentification) {
		return gateway
				.findById(tableIdentification)
				.orElseThrow(() -> new TableRestaurantNotFoundException(tableIdentification));
	}

}
