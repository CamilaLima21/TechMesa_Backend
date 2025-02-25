package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.usecase.exception.ReserveNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetReserve {
	
	private final ReserveGateway gateway;
	
	public Reserve execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new ReserveNotFoundException(id));
	}

}
