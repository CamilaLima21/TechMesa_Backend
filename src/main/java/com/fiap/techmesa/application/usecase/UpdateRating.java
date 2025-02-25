package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.dto.UpdateRatingRequest;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateRating {
	
	private final RatingGateway gateway;
	
	public Rating execute(
			final int id, final UpdateRatingRequest updateRatingRequest) {
		final var ratingFound = 
			gateway
				.findById(id)
				.orElseThrow(() -> new RatingNotFoundException(id));
		
		ratingFound.setTitle(updateRatingRequest.getTitle());
		ratingFound.setText(updateRatingRequest.getText());
		ratingFound.setNote(updateRatingRequest.getNote());
		ratingFound.setDateRegistration(updateRatingRequest.getDateRegistration());
		
		return gateway.update(ratingFound);
	}

}
