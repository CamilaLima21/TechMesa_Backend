package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteRating {

	private final RatingGateway gateway;
	
	public void execute(final Integer id) {
		final var rating =
			gateway
			.findById(id)
			.orElseThrow(() -> new RatingNotFoundException(id));
		
		gateway.delete(rating.getId());
	}
}
