package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.usecase.exception.ReserveNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteReserve {

	private final ReserveGateway gateway;
	
	public void execute(final Integer id) {
		final var reserve =
			gateway
				.findById(id)
				.orElseThrow(() -> new ReserveNotFoundException(id));
		
		gateway.delete(reserve.getId());
	}
}
