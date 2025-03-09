package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateOpeningHours {

	private final OpeningHoursGateway gateway;
	
	public OpeningHours execute(final OpeningHours request) {
		
		final var buildDomain =
				OpeningHours.createOpeningHours(
						request.getRestaurantId(), 
						request.getName(),
						request.getTurn(),
						request.getDayWeek(),
						request.getStartTime(),
						request.getEndTime());
		
		return gateway.save(buildDomain);
		
	}
}
