package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.application.usecase.exception.OpeningHoursNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetOpeningHours {
	
	private final OpeningHoursGateway gateway;
	
	public OpeningHours execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new OpeningHoursNotFoundException(id));
	}

}
