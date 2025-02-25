package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.dto.UpdateReserveRequest;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.usecase.exception.ReserveNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateReserve {
	
	private final ReserveGateway gateway;
	
	public Reserve execute(
			final int id, final UpdateReserveRequest updateReserveRequest) {
		final var reserveFound =
			gateway
				.findById(id)
				.orElseThrow(() -> new ReserveNotFoundException(id));
		
		reserveFound.setNumberPeople(updateReserveRequest.getNumberPeople());
		reserveFound.setDateReserve(updateReserveRequest.getDateReserve());
		reserveFound.setStartReserve(updateReserveRequest.getStartReserve());
		
		return gateway.update(reserveFound);
	}

}
