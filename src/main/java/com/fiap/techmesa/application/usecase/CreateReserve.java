package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.usecase.exception.ReserveAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateReserve {
	
	private final ReserveGateway gateway;
	
	public Reserve execute(final Reserve request, final Integer id) {
		
		final var reserve = gateway.findByRestaurantIdAndClientIdAndData(request.getRestaurantId(), request.getClientId(), request.getDateReserve());
		
		if(reserve.isPresent()) {
			throw new ReserveAlreadyExistsException(request.getRestaurantId(), request.getClientId(), request.getDateReserve());
		}
		
		final var buildDomain =
				Reserve.createReserve(
						request.getClientId(),
						request.getRestaurantId(),
						request.getTableRestaurants(),
						request.getNumberPeople(),
						request.getDateReserve(),
						request.getDateCreated(),
						request.getStartReserve(),
						request.getToleranceMinutes(),
						request.getTimeLimit(),
						request.getStatusReserve());
		
		return gateway.save(buildDomain);
		
	}

}
