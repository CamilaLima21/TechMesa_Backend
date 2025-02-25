package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.application.usecase.exception.OpeningHoursNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteOpeningHours {
	
	private final OpeningHoursGateway gateway;
	
	public void execute(final Integer id) {
		final var openingHours =
			gateway
				.findById(id)
				.orElseThrow(() -> new OpeningHoursNotFoundException(id));
	
		gateway.delete(openingHours.getId());
	}
	
}
