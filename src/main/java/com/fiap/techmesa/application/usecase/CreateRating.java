package com.fiap.techmesa.application.usecase;

import org.springframework.stereotype.Component;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateRating {
	
	private final RatingGateway gateway;
	
	public Rating execute(final Rating request, final Integer id) {
		
		final var rating = gateway.findById(request.getId());
		
		if(rating.isPresent()) {
			throw new RatingAlreadyExistsException(request.getId(), request.getTitle());
		}
		
		final var buildDomain =
				Rating.createRating(
						request.getClientId(),
						request.getTitle(),
						request.getText(),
						request.getNote(),
						request.getDateRegistration());			
		
		return gateway.save(buildDomain);
	}

}
