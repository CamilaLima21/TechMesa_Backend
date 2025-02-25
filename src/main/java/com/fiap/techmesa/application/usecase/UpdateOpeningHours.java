package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.dto.UpdateOpeningHoursRequest;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.application.usecase.exception.OpeningHoursNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateOpeningHours {
	
	private final OpeningHoursGateway gateway;
	
	public OpeningHours execute(
			final int id, final UpdateOpeningHoursRequest updateOpeningHoursRequest) {
		final var openingHoursFound =
				gateway
					.findById(id)
					.orElseThrow(() -> new OpeningHoursNotFoundException(id));
		
		openingHoursFound.setTurn(updateOpeningHoursRequest.getTurn());
		openingHoursFound.setDayWeek(updateOpeningHoursRequest.getDayWeek());
		openingHoursFound.setStartTime(updateOpeningHoursRequest.getStartTime());
		openingHoursFound.setEndTime(updateOpeningHoursRequest.getEndTime());		
		
		return gateway.update(openingHoursFound);
	}

}
