package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetRating {
	
	private final RatingGateway gateway;
	
	public Rating execute(final int id) {
		return gateway
				.findById(id)
				.orElseThrow(() -> new RatingNotFoundException(id));
	}

}
