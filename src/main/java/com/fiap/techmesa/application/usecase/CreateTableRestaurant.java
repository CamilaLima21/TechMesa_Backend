package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.TableRestaurantAlreadyExistsEception;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateTableRestaurant {
	
	private final TableRestaurantGateway gateway;
	
	public TableRestaurant execute(final TableRestaurant request) {
		
		final var tableRestaurant = gateway.findByRestaurantAndDate(request.getTableIdentification(), request.getReserve());
		
		if(tableRestaurant.isPresent()) {
			throw new TableRestaurantAlreadyExistsEception(request.getTableIdentification(), request.getReserve());
		}
		 
		final var buildDomain =
				TableRestaurant.createTableRestaurant(
						request.getTableIdentification(),
						request.getRestaurant(),
						request.getReserve(),
						request.getNumberSeats(),
						request.getStatusTableOccupation(),
						request.getTablePosition());
		
		return gateway.save(buildDomain);
		
	}

}
